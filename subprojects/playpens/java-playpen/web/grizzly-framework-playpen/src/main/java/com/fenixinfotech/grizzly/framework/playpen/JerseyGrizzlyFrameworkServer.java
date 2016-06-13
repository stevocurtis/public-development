package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.apache.commons.io.IOUtils;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.spdy.SpdyAddOn;
import org.glassfish.grizzly.spdy.SpdyMode;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class JerseyGrizzlyFrameworkServer extends FrameworkServerBase
{
    private static final Logger logger = LoggerFactory.getLogger(JerseyGrizzlyFrameworkServer.class);

    private URI uri;
    private HttpServer server;
    private NetworkListener listener;

    public void runServer() throws IOException
    {
        runServer(defaultPort);
    }

    public void runServer(int port) throws IOException
    {
        logger.info("starting grizzly framework server on port {}", port);

        ResourceConfig resourceConfig = new ResourceConfig(GrizzlyJerseyResource.class);
        uri = UriBuilder.fromUri("https://localhost/").port(port).build();
        server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, true, createSSLContextConfigurator());

        listener = server.getListeners().iterator().next();

        listener.setSecure(true);
        NIOTransport nioTransport = TCPNIOTransportBuilder.newInstance()
                .setReuseAddress(true)
                .setIOStrategy(WorkerThreadIOStrategy.getInstance())
                .setSelectorThreadPoolConfig(ThreadPoolConfig.defaultConfig().setCorePoolSize(2).setMaxPoolSize(4))
                .setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setCorePoolSize(3).setMaxPoolSize(20))
                .build();
        listener.setTransport((TCPNIOTransport)nioTransport);

        SpdyAddOn spdyAddOn = new SpdyAddOn(SpdyMode.NPN);
        listener.registerAddOn(spdyAddOn);

        server.start();

        logger.info("bootstrap of grizzly framework server complete, running on {}", uri);
    }

    protected SSLEngineConfigurator createSSLContextConfigurator() throws IOException
    {
        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        ClassLoader classLoader = getClass().getClassLoader();
        byte[] content = IOUtils.toByteArray(classLoader.getResourceAsStream("keystore.jks"));
        sslContextConfigurator.setKeyStoreBytes(content);
        sslContextConfigurator.setKeyStorePass("changeit");

        sslContextConfigurator.validateConfiguration(true);

        SSLEngineConfigurator result = new SSLEngineConfigurator(
                sslContextConfigurator.createSSLContext(),
                false,
                false,
                false);
        result.setClientMode(false);
        return result;
    }


    public void stopServer() throws IOException
    {
        logger.info("stopping grizzly framework server");

        if (null != server)
            server.shutdownNow();

        logger.info("stopping of grizzly framework server complete");
    }

    public static void main(String[] args)
    {
        JerseyGrizzlyFrameworkServer jerseyGrizzlyFrameworkServer = new JerseyGrizzlyFrameworkServer();
        try
        {
            jerseyGrizzlyFrameworkServer.runServer();
            while (true)
            {}
        }
        catch (IOException e)
        {
            logger.error("error thrown starting application", e);
        }
        finally
        {
            try
            {
                jerseyGrizzlyFrameworkServer.stopServer();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

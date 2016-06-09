package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import com.fenixinfotech.web.common.JerseyResource;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.spdy.PushResource;
import org.glassfish.grizzly.spdy.SpdyAddOn;
import org.glassfish.grizzly.spdy.SpdyMode;
import org.glassfish.grizzly.spdy.SpdyStream;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JerseyGrizzlyFrameworkServer extends FrameworkServerBase
{
    private static final Logger logger = LoggerFactory.getLogger(JerseyGrizzlyFrameworkServer.class);

    private HttpServer server;
    private NetworkListener listener;
    public static final String localhost = "localhost";

    public void runServer() throws IOException
    {
        runServer(defaultPort);
    }

    public void runServer(int port) throws IOException
    {
        logger.info("starting grizzly framework server on port {}", port);

        ResourceConfig resourceConfig = new ResourceConfig(JerseyResource.class);
        URI uri = UriBuilder.fromUri("http://" + localhost + ":" + port).build();
        server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);

        listener = server.getListeners().iterator().next();
        listener.setSecure(true);

        NIOTransport nioTransport = TCPNIOTransportBuilder.newInstance()
                .setReuseAddress(true)
                .setIOStrategy(WorkerThreadIOStrategy.getInstance())
                .setSelectorThreadPoolConfig(ThreadPoolConfig.defaultConfig()
                        .setCorePoolSize(2)
                        .setMaxPoolSize(4))
                .setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig()
                        .setCorePoolSize(3)
                        .setMaxPoolSize(20))
                .build();
        listener.setTransport((TCPNIOTransport)nioTransport);

        SpdyAddOn spdyAddOn = new SpdyAddOn(SpdyMode.PLAIN);
        listener.registerAddOn(spdyAddOn);

        server.addListener(listener);

        final ServerConfiguration serverConfiguration = server.getServerConfiguration();
        serverConfiguration.addHttpHandler(new HttpHandler()
                                           {
                                               public void service(Request request, Response response) throws Exception
                                               {
                                                   long startTime = new Date().getTime();
//                                                   PushResource pushResource = PushResource.builder()
                                                   // Get SPDY stream if it exists
                                                   final SpdyStream spdyStream = (SpdyStream) request.getAttribute(SpdyStream.SPDY_STREAM_ATTRIBUTE);
                                                   // if spdy stream is null it is not a SPDY based request
                                                   if (spdyStream != null)
                                                   {
                                                       // push the content
                                                        logger.info("found a SPDY stream");
                                                   }
                                                   final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.UK);
                                                   final String date = format.format(new Date(System.currentTimeMillis()));
                                                   response.setContentType("text/plain");
                                                   response.setContentLength(date.length());
                                                   response.getWriter().write(date);
//                                                   dumpStats();
                                                   logger.info("processed request in {} ms", new Date().getTime() - startTime);
                                               }
                                           }
        );

        server.start();

        logger.info("bootstrap of grizzly framework server complete, running on http://{}:{}", localhost, port);
    }

    public void stopServer() throws IOException
    {
        logger.info("stopping grizzly framework server");

        // Before shutdown dump thread info
        dumpStats();

        if (null != server)
            server.shutdownNow();

        logger.info("stopping of grizzly framework server complete");
    }

    private void dumpStats()
    {
        logger.info("available processors {}", Runtime.getRuntime().availableProcessors());
        logger.info("server configuration name {}", server.getServerConfiguration().getName());
        logger.info("listener io strategy {}", listener.getTransport().getIOStrategy());
        if (null != listener.getTransport())
        {
            logger.info("server connection backlog {}", listener.getTransport().getServerConnectionBackLog());

            logger.info("kernel thread pool queue {}/{} threads", listener.getTransport().getKernelThreadPoolConfig().getCorePoolSize(),
                    listener.getTransport().getKernelThreadPoolConfig().getMaxPoolSize());
            logger.info("kernel thread pool queue {}", listener.getTransport().getKernelThreadPoolConfig().getQueue());
//            logger.info("kernel thread pool queue size {}", ((GrizzlyExecutorService)listener.getTransport().getKernelThreadPool()).getConfiguration().getQueue().size());

            logger.info("worker thread pool queue {}/{} threads", listener.getTransport().getWorkerThreadPoolConfig().getCorePoolSize(),
                    listener.getTransport().getWorkerThreadPoolConfig().getMaxPoolSize());
            logger.info("worker thread pool queue {}", listener.getTransport().getWorkerThreadPoolConfig().getQueue());
//            logger.info("worker thread pool queue size {}", ((GrizzlyExecutorService)listener.getTransport().getWorkerThreadPool()).getConfiguration().getQueue().size());
        }
        else
        {
            logger.warn("transport is NULL");
        }

    }
}

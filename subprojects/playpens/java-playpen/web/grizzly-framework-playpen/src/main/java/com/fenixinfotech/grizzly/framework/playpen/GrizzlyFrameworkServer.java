package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GrizzlyFrameworkServer extends FrameworkServerBase
{
    private static final Logger logger = LoggerFactory.getLogger(GrizzlyFrameworkServer.class);

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

        server = new HttpServer();

        listener = new NetworkListener("grizzly", localhost, port);
        listener.setSecure(false);

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

        server.addListener(listener);
        server.start();

        logger.info("bootstrap of grizzly framework server complete, running on http://{}:{}", localhost, port);
    }

    public void stopServer() throws IOException
    {
        logger.info("stopping grizzly framework server");

        if (null != server)
            server.shutdownNow();

        logger.info("stopping of grizzly framework server complete");
    }
}

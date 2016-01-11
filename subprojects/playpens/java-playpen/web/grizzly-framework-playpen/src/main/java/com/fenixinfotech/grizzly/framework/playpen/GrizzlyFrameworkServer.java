package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
//        listener.getTransport().setIOStrategy(WorkerThreadIOStrategy.getInstance());

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

        final ServerConfiguration serverConfiguration = server.getServerConfiguration();
        serverConfiguration.addHttpHandler(new HttpHandler()
                                           {
                                               public void service(Request request, Response response) throws Exception
                                               {
                                                   long startTime = new Date().getTime();
                                                   final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.UK);
                                                   final String date = format.format(new Date(System.currentTimeMillis()));
                                                   response.setContentType("text/plain");
                                                   response.setContentLength(date.length());
                                                   response.getWriter().write(date);
                                                   dumpStats();
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
//            for (ConnectionProbe probe : listener.getTransport().getConnectionMonitoringConfig().getProbes())
//            {
//                logger.info("connection probe {}", probe);
//            }
//            for (ThreadPoolProbe probe : listener.getTransport().getThreadPoolMonitoringConfig().getProbes())
//            {
//                logger.info("thread pool probe {}", probe);
//            }
            logger.info("server connection backlog {}", listener.getTransport().getServerConnectionBackLog());

            logger.info("kernel thread pool queue {}/{} threads", listener.getTransport().getKernelThreadPoolConfig().getCorePoolSize(),
                    listener.getTransport().getKernelThreadPoolConfig().getMaxPoolSize());
            logger.info("kernel thread pool queue {}", listener.getTransport().getKernelThreadPoolConfig().getQueue());
            logger.info("kernel thread pool queue size {}", ((GrizzlyExecutorService)listener.getTransport().getKernelThreadPool()).getConfiguration().getQueue().size());

            logger.info("worker thread pool queue {}/{} threads", listener.getTransport().getWorkerThreadPoolConfig().getCorePoolSize(),
                    listener.getTransport().getWorkerThreadPoolConfig().getMaxPoolSize());
            logger.info("worker thread pool queue {}", listener.getTransport().getWorkerThreadPoolConfig().getQueue());
            logger.info("worker thread pool queue size {}", ((GrizzlyExecutorService)listener.getTransport().getWorkerThreadPool()).getConfiguration().getQueue().size());
        }
        else
        {
            logger.warn("transport is NULL");
        }

    }
}

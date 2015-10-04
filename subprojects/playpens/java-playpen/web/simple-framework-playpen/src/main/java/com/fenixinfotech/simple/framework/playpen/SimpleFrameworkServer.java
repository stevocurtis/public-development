package com.fenixinfotech.simple.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

class SimpleFrameworkServer extends FrameworkServerBase implements Container
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleFrameworkServer.class);

    private Container container;
    private Server server;
    private Connection connection;
    private SocketAddress address;
    public static final String defaultResponseBody = "Hello World";

    public void handle(Request request, Response response)
    {
        logger.info("received request {} and response {}", request, response);

        try
        {
            PrintStream body = response.getPrintStream();
            long time = System.currentTimeMillis();

            response.setValue("Content-Type", "text/plain");
            response.setValue("Server", "SimpleFrameworkServer/1.0 (Simple 5.0.4)");
            response.setDate("Date", time);
            response.setDate("Last-Modified", time);

            body.println(defaultResponseBody);
            body.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        logger.info("processed request {} and response {}", request, response);
    }

    public void runServer() throws IOException
    {
        runServer(defaultPort);
    }

    public void runServer(int port) throws IOException
    {
        logger.info("starting simple framework server on port {}", port);

        container = new SimpleFrameworkServer();
        server = new ContainerServer(container);
        connection = new SocketConnection(server);
        address = new InetSocketAddress(port);
        connection.connect(address);

        logger.info("bootstrap of simple framework server complete, running on http://localhost:{}", port);
    }

    public void stopServer() throws IOException
    {
        logger.info("stopping simple framework server");

        if (null != server)
            server.stop();

        logger.info("stopping of simple framework server complete");
    }

    public static void main(String[] args) throws IOException
    {
        new SimpleFrameworkServer().runServer();
    }
}
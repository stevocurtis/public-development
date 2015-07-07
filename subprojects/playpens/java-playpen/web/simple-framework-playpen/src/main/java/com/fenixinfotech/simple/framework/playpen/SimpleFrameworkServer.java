package com.fenixinfotech.simple.framework.playpen;

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

class SimpleFrameworkServer implements Container
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleFrameworkServer.class);

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

            body.println("Hello World");
            body.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        logger.info("processed request {} and response {}", request, response);
    }

    public void runServer(String[] args) throws IOException
    {
        logger.info("starting simple framework server");

        Container container = new SimpleFrameworkServer();
        Server server = new ContainerServer(container);
        Connection connection = new SocketConnection(server);
        SocketAddress address = new InetSocketAddress(8181); // TODO data drive this port
        connection.connect(address);

        logger.info("bootstrap of simple framework server complete");
    }

    public static void main(String[] args) throws IOException
    {
        new SimpleFrameworkServer().runServer(args);
    }
}
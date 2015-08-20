package com.fenixinfotech.simple.framework.playpen;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.simple.SimpleContainerFactory;
import org.glassfish.jersey.simple.SimpleServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

class JerseySimpleFrameworkServer extends FrameworkServerBase
{
    private static final Logger logger = LoggerFactory.getLogger(JerseySimpleFrameworkServer.class);

    private SimpleServer server;

    public void runServer() throws IOException
    {
        runServer(defaultPort);
    }

    public void runServer(int port) throws IOException
    {
        logger.info("starting JrestySimpleFrameworkServer server on port {}", port);

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        ResourceConfig config = new ResourceConfig(JerseyResource.class);
        server = SimpleContainerFactory.create(baseUri, config);

        logger.info("bootstrap of JrestySimpleFrameworkServer server complete, running on http://localhost:{}", port);
    }

    public void stopServer() throws IOException
    {
        logger.info("stopping JrestySimpleFrameworkServer");

        if (null != server)
            server.close();

        logger.info("stopping of JrestySimpleFrameworkServer complete");
    }

    public static void main(String[] args) throws IOException
    {
        new JerseySimpleFrameworkServer().runServer();
    }
}
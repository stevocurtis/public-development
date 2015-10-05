package com.fenixinfotech.simple.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import com.fenixinfotech.web.common.JerseyResource;
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
        logger.info("starting JerseySimpleFrameworkServer server on port {}", port);

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        ResourceConfig config = new ResourceConfig(JerseyResource.class);
        server = SimpleContainerFactory.create(baseUri, config);

        logger.info("bootstrap of JerseySimpleFrameworkServer server complete, running on http://localhost:{}", port);
    }

    public void stopServer() throws IOException
    {
        logger.info("stopping JerseySimpleFrameworkServer");

        if (null != server)
            server.close();

        logger.info("stopping of JerseySimpleFrameworkServer complete");
    }

    public static void main(String[] args) throws IOException
    {
        new JerseySimpleFrameworkServer().runServer();
    }
}
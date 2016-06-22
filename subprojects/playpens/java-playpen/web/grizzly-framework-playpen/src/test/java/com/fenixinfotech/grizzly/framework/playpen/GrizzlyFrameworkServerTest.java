package com.fenixinfotech.grizzly.framework.playpen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GrizzlyFrameworkServerTest
{
    GrizzlyFrameworkServer grizzlyFrameworkServer;
    int port = 4321;

    @Before
    public void init() throws IOException
    {
        grizzlyFrameworkServer = new GrizzlyFrameworkServer();
        grizzlyFrameworkServer.runServer(port);
    }

    @After
    public void tidyup() throws IOException
    {
        grizzlyFrameworkServer.stopServer();
    }

    @Test
    public void testRunServer() throws Exception
    {
        // Add some verification
//        Thread.sleep(3000);
//        while(true) {}
    }
}
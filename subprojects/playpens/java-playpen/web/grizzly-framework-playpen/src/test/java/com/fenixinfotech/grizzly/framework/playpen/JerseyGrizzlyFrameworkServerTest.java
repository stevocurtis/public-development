package com.fenixinfotech.grizzly.framework.playpen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class JerseyGrizzlyFrameworkServerTest
{
    JerseyGrizzlyFrameworkServer grizzlyFrameworkServer;

    @Before
    public void init() throws IOException
    {
        grizzlyFrameworkServer = new JerseyGrizzlyFrameworkServer();
        grizzlyFrameworkServer.runServer();
    }

    @After
    public void tidyup() throws IOException
    {
        if (grizzlyFrameworkServer != null)
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
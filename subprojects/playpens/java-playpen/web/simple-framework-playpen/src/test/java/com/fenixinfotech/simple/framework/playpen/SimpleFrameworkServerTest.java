package com.fenixinfotech.simple.framework.playpen;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.Exception;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class SimpleFrameworkServerTest
{
    private static final Logger logger = LoggerFactory.getLogger(SimpleFrameworkServerTest.class);
    SimpleFrameworkServer simpleFrameworkServer;

    private int portNumber = 6789;

    @Before
    public void init() throws IOException
    {
        simpleFrameworkServer = new SimpleFrameworkServer();
        simpleFrameworkServer.runServer(portNumber);
    }


    @After
    public void tidyup() throws IOException
    {
        simpleFrameworkServer.stopServer();
    }

    @Test
    public void testRunServer() throws Exception
    {
        assertNotNull(simpleFrameworkServer);

        logger.info("testing http GET");

        HttpUriRequest getRequest = new HttpGet( "http://localhost:" + portNumber);
        HttpResponse getResponse = HttpClientBuilder.create().build().execute(getRequest);

        assertEquals(HttpStatus.SC_OK,                          getResponse.getStatusLine().getStatusCode());
        assertEquals(SimpleFrameworkServer.defaultResponseBody, EntityUtils.toString(getResponse.getEntity()).trim());

        logger.info("testing http POST");

        HttpUriRequest postRequest = new HttpPost( "http://localhost:" + portNumber);
        HttpResponse postResponse = HttpClientBuilder.create().build().execute(postRequest);

        assertEquals(HttpStatus.SC_OK,                          postResponse.getStatusLine().getStatusCode());
        assertEquals(SimpleFrameworkServer.defaultResponseBody, EntityUtils.toString(postResponse.getEntity()).trim());
    }
}
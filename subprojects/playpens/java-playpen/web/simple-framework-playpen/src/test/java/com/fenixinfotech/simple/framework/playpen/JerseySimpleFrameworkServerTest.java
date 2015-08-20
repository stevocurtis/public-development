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

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JerseySimpleFrameworkServerTest
{
    private static final Logger logger = LoggerFactory.getLogger(JerseySimpleFrameworkServerTest.class);
    JerseySimpleFrameworkServer simpleFrameworkServer;

    private int portNumber = 4321; // Set different from default since on CI server can take a while to shut down between tests

    @Before
    public void init() throws IOException
    {
        simpleFrameworkServer = new JerseySimpleFrameworkServer();
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

        HttpUriRequest getRequest = new HttpGet( "http://localhost:" + portNumber + "/jerseyresource");
        HttpResponse getResponse = HttpClientBuilder.create().build().execute(getRequest);

        assertEquals(HttpStatus.SC_OK,                          getResponse.getStatusLine().getStatusCode());
        assertEquals(JerseySimpleFrameworkServer.defaultResponseBody, EntityUtils.toString(getResponse.getEntity()).trim());

        logger.info("testing http POST");

        HttpUriRequest postRequest = new HttpPost( "http://localhost:" + portNumber + "/jerseyresource");
        HttpResponse postResponse = HttpClientBuilder.create().build().execute(postRequest);

        assertEquals(HttpStatus.SC_OK,                          postResponse.getStatusLine().getStatusCode());
        assertEquals(JerseySimpleFrameworkServer.defaultResponseBody, EntityUtils.toString(postResponse.getEntity()).trim());
    }
}
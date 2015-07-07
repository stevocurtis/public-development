package com.fenixinfotech.simple.framework.playpen;

import com.fenixinfotech.simple.framework.playpen.SimpleFrameworkServer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.Exception;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SimpleFrameworkServerTest
{
    SimpleFrameworkServer simpleFrameworkServer;

    @Before
    public void init() throws IOException
    {
        simpleFrameworkServer = new SimpleFrameworkServer();
        simpleFrameworkServer.runServer();
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
        HttpUriRequest request = new HttpGet( "http://localhost:" + SimpleFrameworkServer.defaultPort);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        int    responseCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity()).trim();

        assertEquals(HttpStatus.SC_OK,                          responseCode);
        assertEquals(SimpleFrameworkServer.defaultResponseBody, responseBody);
    }
}
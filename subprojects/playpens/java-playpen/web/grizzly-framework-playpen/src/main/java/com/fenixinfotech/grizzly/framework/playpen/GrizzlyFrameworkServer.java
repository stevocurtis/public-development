package com.fenixinfotech.grizzly.framework.playpen;

import com.fenixinfotech.web.common.FrameworkServerBase;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GrizzlyFrameworkServer extends FrameworkServerBase
{
    private static final Logger logger = LoggerFactory.getLogger(GrizzlyFrameworkServer.class);

    public void runServer() throws IOException {
        HttpServer httpServer = HttpServer.createSimpleServer();
        httpServer.getServerConfiguration().addHttpHandler(new HttpHandler() {
                                                               public void service(Request requestm, Response response) throws Exception {
                                                                   final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.UK);
                                                                   final String date = format.format(new Date(System.currentTimeMillis()));
                                                                   response.setContentType("text/plain");
                                                                   response.setContentLength(date.length());
                                                                   response.getWriter().write(date);
                                                               }
                                                           },
                "/hello"
        );

        httpServer.start();
    }
}

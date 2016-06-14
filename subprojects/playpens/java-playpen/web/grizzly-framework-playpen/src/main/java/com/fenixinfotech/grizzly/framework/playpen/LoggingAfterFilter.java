package com.fenixinfotech.grizzly.framework.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class LoggingAfterFilter implements ContainerResponseFilter
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingAfterFilter.class);


    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException
    {
        logger.info("handling response filter .....");
    }
}
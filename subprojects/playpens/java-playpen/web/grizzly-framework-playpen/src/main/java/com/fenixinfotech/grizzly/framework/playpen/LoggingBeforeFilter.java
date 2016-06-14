package com.fenixinfotech.grizzly.framework.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class LoggingBeforeFilter implements ContainerRequestFilter
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingBeforeFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException
    {
        logger.info("handling request filter .....");
    }
}
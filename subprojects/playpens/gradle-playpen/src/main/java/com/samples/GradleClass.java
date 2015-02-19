package com.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GradleClass
{
    private static Logger logger = LoggerFactory.getLogger(GradleClass.class);

    public static void main (String[] args)
    {
        new GradleClass().run(args);
    }

    public int run(String[] args)
    {
        logger.info("Received args: {}", args);
        return (args == null ? 0 : args.length);
    }
}

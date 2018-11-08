package com.fenixinfotech.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackApp
{
    private static final Logger logger = LoggerFactory.getLogger(LogbackApp.class);

    public void run(Object... args) {
        logger.info("in run method with args {}", args);
    }
}
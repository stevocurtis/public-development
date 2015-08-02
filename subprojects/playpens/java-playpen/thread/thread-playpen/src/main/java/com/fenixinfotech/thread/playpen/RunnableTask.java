package com.fenixinfotech.thread.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableTask implements Runnable
{
    public static final Logger logger = LoggerFactory.getLogger(RunnableTask.class);

    public void run()
    {
        logger.info("executing thread run");
    }
}

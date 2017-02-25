package com.fenixinfotech.thread.playpen;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorTask implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(MonitorTask.class);
    private ExecutorService executorService = null;
    private String threadIdentifier;
    private static final long maxDurationMillis = 500L;

    public MonitorTask(ExecutorService executorService, String threadIdentifier)
    {
        this.executorService = executorService;
        this.threadIdentifier = threadIdentifier;
    }

    public void run()
    {
        long started = new Date().getTime();
        while (new Date().getTime() < (started + maxDurationMillis))
        {
            if (executorService instanceof ThreadPoolExecutor)
            {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
                logger.info("thread pool {} pool size {} core pool size {} active count {} queue size {} largest pool size {} completed task count {} task count {}",
                        this.threadIdentifier,
                        threadPoolExecutor.getPoolSize(),
                        threadPoolExecutor.getCorePoolSize(),
                        threadPoolExecutor.getActiveCount(),
                        threadPoolExecutor.getQueue().size(),
                        threadPoolExecutor.getLargestPoolSize(),
                        threadPoolExecutor.getCompletedTaskCount(),
                        threadPoolExecutor.getTaskCount());
            }
            else
            {
                logger.warn("unable to get monitoring info from executorService since it is not an instance of ThreadPoolExecutor");
            }

            // Go to sleep so don't generate too much log, Travis CI has a 4Mb limit
            try {
                Thread.sleep(maxDurationMillis/10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

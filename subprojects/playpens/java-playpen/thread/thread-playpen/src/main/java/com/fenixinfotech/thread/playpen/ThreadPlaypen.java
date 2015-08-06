package com.fenixinfotech.thread.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPlaypen
{
    private static final Logger logger = LoggerFactory.getLogger(ThreadPlaypen.class);

    public void startBasicThreads(int numThreads)
    {
        logger.info("creating {} basic threads", numThreads);

        for (int i=0; i<numThreads; i++)
        {
            Thread thread = new Thread(new RunnableTask());
            thread.start();
        }

        logger.info("processed all threads");
    }

    public void startFixedThreadPool(int threadPoolSize, int numThreads)
    {
        logger.info("creating fixed thread pool of size {} with {} threads", threadPoolSize, numThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        // start monitoring thread
        Runnable monitoringWorker = new MonitorTask(executorService, "fixed pool");
        executorService.execute(monitoringWorker);

        for(int i=0; i<numThreads; i++)
        {
            Runnable worker = new RunnableTask();
            executorService.execute(worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated())
        {}

        logger.info("processed all threads");
    }
}

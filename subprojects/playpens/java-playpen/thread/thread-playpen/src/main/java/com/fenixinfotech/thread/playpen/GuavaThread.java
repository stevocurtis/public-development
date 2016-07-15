package com.fenixinfotech.thread.playpen;

import com.google.common.util.concurrent.Striped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;

public class GuavaThread {

    private static final Logger logger = LoggerFactory.getLogger(GuavaThread.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Striped<ReadWriteLock> stripe = Striped.readWriteLock(10);

    public void fineGrainedConcurrency() throws InterruptedException {

        for (int i=1; i<=10; i++) {
            runThread("one");
            runThread("two");
            runThread("one");
            i++;
        }

        executorService.shutdown();
        executorService.awaitTermination(300, TimeUnit.SECONDS);
    }

    public void runThread(String id) {

        // Get lock if possible
        ReadWriteLock readWriteLock = null;
        try {
            readWriteLock = stripe.get(id);
            readWriteLock.readLock().lock();
            readWriteLock.writeLock().lock();
            MyThread myThread = new MyThread(id);
            executorService.execute(myThread);
//            myThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        finally {
            if (readWriteLock != null) {
                readWriteLock.readLock().unlock();
                readWriteLock.writeLock().unlock();
            }
        }
    }

    private class MyThread extends Thread {

        String id;

        public MyThread(String id) {
            super();
            this.id = id;
        }

        @Override
        public void run() {
            logger.info("running thread {}", id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("finished thread {}", id);
        }
    }
}

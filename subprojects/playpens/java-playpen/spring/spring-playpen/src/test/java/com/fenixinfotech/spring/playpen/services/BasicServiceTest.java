package com.fenixinfotech.spring.playpen.services;

import com.fenixinfotech.spring.playpen.BasicServiceTestAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BasicServiceTestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class BasicServiceTest
{
    @Autowired
    BasicService basicService;
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static final int numberOfExecutions = 10;

    @Test
    public void testBasicService()
    {
        assertNotNull(basicService);
        assertNotNull(threadPoolTaskExecutor);

        for (int i=1; i<=numberOfExecutions; i++)
        {
            threadPoolTaskExecutor.execute(basicService);
        }

        for (;;)
        {
            int count = threadPoolTaskExecutor.getActiveCount();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if (count == 0)
            {
                threadPoolTaskExecutor.shutdown();
                break;
            }
        }
    }
}
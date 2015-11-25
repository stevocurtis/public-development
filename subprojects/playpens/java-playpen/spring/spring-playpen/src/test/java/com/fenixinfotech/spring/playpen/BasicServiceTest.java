package com.fenixinfotech.spring.playpen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class BasicServiceTest
{
    @Autowired
    BasicService basicService;

    private static final int threadPoolSize     = 3;
    private static final int numberOfExecutions = 10;

    @Test
    public void testBasicService()
    {
        assertNotNull(basicService);

        // Create a thread pool to execute the spring service
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);

        for (int i=1; i<=numberOfExecutions; i++)
        {
            threadPoolExecutor.execute(new Runnable()
            {
                public void run()
                {
                    basicService.execute();
                }
            });
//            basicService.execute();
        }
    }
}
package com.fenixinfotech.spring.playpen.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BasicService implements Runnable
{
    Logger logger = LoggerFactory.getLogger(BasicService.class);

    public void run()
    {
        logger.info("running {} spring service at {}", getClass().getSimpleName(), getFormattedDate());

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        logger.info("finished {} spring service at {}", getClass().getSimpleName(), getFormattedDate());
    }

    private String getFormattedDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date());
    }
}
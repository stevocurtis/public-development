package com.fenixinfotech.spring.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BasicService
{
    Logger logger = LoggerFactory.getLogger(BasicService.class);

    public void execute()
    {
        logger.info("running execute on spring service {}", getClass().getSimpleName());
    }
}
package com.fenixinfotech.aspectj.playpen;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Aspect
public class LoggingAspect
{
    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution (* *(..))")
    public Object aroundLogMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        long startTimestamp = new Date().getTime();
        Object result = proceedingJoinPoint.proceed();
        long elapsedTime = new Date().getTime() - startTimestamp;

        logger.info("method {} with args {} returned {} in {} millis",
                proceedingJoinPoint.getStaticPart(),
                proceedingJoinPoint.getArgs(),
                result,
                elapsedTime);

        return result;
    }
}

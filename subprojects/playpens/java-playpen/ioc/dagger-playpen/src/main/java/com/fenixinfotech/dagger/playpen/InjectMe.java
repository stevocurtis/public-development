package com.fenixinfotech.dagger.playpen;

import dagger.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Module
public class InjectMe
{
    private static final Logger logger = LoggerFactory.getLogger(InjectMe.class);

    public boolean doSomething()
    {
        logger.info("Just called doSomething");

        // Return something so this can be verified from unit test
        return true;
    }
}

package com.fenixinfotech.dagger.playpen;

import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class InjectMeTest
{
    @Inject
    InjectMe injectMe;

    @Test
    public void testDoSomething() throws Exception
    {
//        assertNotNull(injectMe);
//        assertTrue(injectMe.doSomething());
    }
}
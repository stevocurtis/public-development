package com.fenixinfotech.aspectj.playpen;

import org.junit.Test;

import static org.junit.Assert.*;

public class AspectedFunctionalityTest
{
    @Test
    public void testPower() throws Exception
    {
        assertEquals(8, new AspectedFunctionality().power(2, 3), 0);
    }
}
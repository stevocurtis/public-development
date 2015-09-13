package com.fenixinfotech.aspectj.playpen;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 700608667 on 11/09/2015.
 */
public class AspectedFunctionalityTest
{

    @Test
    public void testPower() throws Exception
    {
        new AspectedFunctionality().power(2, 3);
    }
}
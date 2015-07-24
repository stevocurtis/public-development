package com.fenixinfotech.jmx.playpen;

import com.fenixinfotech.jmx.playpen.MbeanMonitor;

import static org.junit.Assert.*;

public class MbeanMonitorTest
{

    @org.junit.Test
    public void testConnect() throws Exception
    {
        new MbeanMonitor();
        assertTrue(true); // TODO add real test
    }
}
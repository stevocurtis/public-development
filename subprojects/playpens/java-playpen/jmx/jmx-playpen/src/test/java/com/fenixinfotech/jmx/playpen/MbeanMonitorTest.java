package com.fenixinfotech.jmx.playpen;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MbeanMonitorTest
{
    @Test
    public void testConnect() throws Exception
    {
        MbeanMonitor mbeanMonitor = new MbeanMonitor();
        mbeanMonitor.connect("localhost", "4444");
        assertTrue(true); // TODO add real test
    }
}
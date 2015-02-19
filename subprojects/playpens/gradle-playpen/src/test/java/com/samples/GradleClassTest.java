package com.samples;

import org.junit.Test;

import static org.junit.Assert.*;

public class GradleClassTest
{
    @Test
    public void testGradleClass()
    {
        assertEquals(0, new GradleClass().run(null));
        assertEquals(1, new GradleClass().run(new String[]{""}));
        assertEquals(1, new GradleClass().run(new String[]{"a"}));
        assertEquals(3, new GradleClass().run(new String[]{"a", "b", "c"}));
    }
}
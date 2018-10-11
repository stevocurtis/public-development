package com.fenixinfotech.streams.playpen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamUtilTest {

    StreamUtil streamUtil;
    String[] emptyData;
    String[] data;

    @Before
    public void setUp() throws Exception {
        streamUtil = new StreamUtil();
        emptyData = new String[] {};
        data = new String[] {"Who", "What", "Where", "When", "Why", "When"};
    }

    @Test
    public void count() {
        assertEquals(0, streamUtil.count(null));
        assertEquals(0, streamUtil.count(emptyData));
        assertEquals(6, streamUtil.count(data));
    }
}
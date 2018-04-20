package com.fenixinfotech.rxjava.playpen;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StandardStreamsTest {

    StandardStreams standardStreams;

    @Before
    public void initTest() {
        standardStreams = new StandardStreams();
        assertNotNull(standardStreams);
    }

    @Test
    public void simpleMap() {
        assertEquals(0, standardStreams.simpleMap(null).size());
        assertEquals(0, standardStreams.simpleMap(buildTestData(0)).size());
        assertEquals(1, standardStreams.simpleMap(buildTestData(1)).size());
        assertEquals(2, standardStreams.simpleMap(buildTestData(2)).size());
        assertEquals(20, standardStreams.simpleMap(buildTestData(20)).size());
        assertEquals(10, standardStreams.simpleMap(buildTestData(20, true)).size());
    }

    private List<String> buildTestData(int numEntries) {
        return buildTestData(numEntries, false);
    }

    private List<String> buildTestData(int numEntries, boolean withNulls) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= numEntries; i++) {
            if (withNulls && (i % 2 == 0)) {
                list.add(null);
            } else {
                list.add(String.format("Test Data %d", i));
            }
        }
        return list;
    }
}
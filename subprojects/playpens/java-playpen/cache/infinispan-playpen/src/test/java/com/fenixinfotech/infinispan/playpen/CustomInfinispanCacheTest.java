package com.fenixinfotech.infinispan.playpen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomInfinispanCacheTest {

    CustomInfinispanCache customInfinispanCache;

    @Before
    public void init() {
        customInfinispanCache = new CustomInfinispanCache();
    }

    @After
    public void teardown() {
        customInfinispanCache.closeCache();
    }

    @Test
    public void testCache() {
        int maxCacheEntries = 10;

        // Store some data
        for (int i = 1; i <= maxCacheEntries; i++) {
            customInfinispanCache.putInCache(String.valueOf(i), String.format("value %d", i));
        }

        // Check cache content
        for (int i = 1; i <= maxCacheEntries; i++) {
            Object cachedValue = customInfinispanCache.getFromCache(String.valueOf(i));
            assertNotNull(cachedValue);
            assertEquals(String.format("value %d", i), cachedValue);
        }
    }

}
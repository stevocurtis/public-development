package com.fenixinfotech.infinispan.playpen;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomInfinispanCacheTest {

    @Test
    public void testCache()
    {
        String testCacheName = "testCache";
        int maxCacheEntries = 10;

        CustomInfinispanCache customInfinispanCache = new CustomInfinispanCache();

        // Store some data
        for (int i=1; i<=maxCacheEntries; i++)
        {
            customInfinispanCache.putInCache(String.valueOf(i), String.format("value %d", i));
        }

        // Check cache content
        for (int i=1; i<=maxCacheEntries; i++)
        {
            Object cachedValue = customInfinispanCache.getFromCache(String.valueOf(i));
            assertNotNull(cachedValue);
            assertEquals(String.format("value %d", i), cachedValue);
        }
    }

}
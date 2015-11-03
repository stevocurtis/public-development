package com.fenixinfotech.ehcache.playpen;

import net.sf.ehcache.CacheManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CustomEhCacheTest
{
    private static final Logger logger = LoggerFactory.getLogger(CustomEhCacheTest.class);

    @Test
    public void testCache()
    {
        assertTrue(false);
        String testCacheName = "testCache";
        int maxCacheEntries = 10;

        CustomEhCache customEhCache = new CustomEhCache();

        // Store some data
        for (int i=1; i<=maxCacheEntries; i++)
        {
            customEhCache.putInCache(testCacheName, i, String.format("value %d", i));
        }

        // Check cache content
        for (int i=1; i<=maxCacheEntries; i++)
        {
            Object cachedValue = customEhCache.getFromCache(testCacheName, i);
            assertNotNull(cachedValue);
            assertEquals(String.format("value %d", i), cachedValue);
        }
    }
}
package com.fenixinfotech.infinispan.playpen;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;

import static org.junit.Assert.*;

public class CustomInfinispanCacheTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomInfinispanCacheTest.class);
    CustomInfinispanCache customInfinispanCache;
    long clusterUpdateSleepDuration = 3000L;
    long clusterReadSleepDuration = 1000L;

    @After
    public void teardown() {
        if (customInfinispanCache != null) {
            customInfinispanCache.closeCache();
        }
    }

    @Test
    public void testGetInfinispanConfig() {
        File configFile = new CustomInfinispanCache().getInfinispanConfig();
        assertNotNull(configFile);
        assertTrue(configFile.exists());
        assertTrue(configFile.isFile());
    }

    @Test
    public void testCache() {
        customInfinispanCache = new CustomInfinispanCache();
        storeAndReadData();
    }

    /**
     * Test cluster mode, need to seed the cache population with data
     * so can see traffic arriving from different instances.
     */
//    Only turn this on locally otherwise will kill CI servers
//    @Test
    public void testClusteredCache() {
        customInfinispanCache = new CustomInfinispanCache();
        storeClusterData();
    }

    /**
     * Test cluster mode, need to seed the cache population with data
     * so can see traffic arriving from different instances.
     */
//    Only turn this on locally otherwise will kill CI servers
//    @Test
    public void testClusteredCacheRead() {
        customInfinispanCache = new CustomInfinispanCache();
        readClusterData();
    }

    private void storeAndReadData() {
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

    private void storeClusterData() {
        long sleepDuration = 1000L;
        String clusterNodeIdentifier = UUID.randomUUID().toString();

        // Store some data
        for (int i = 1; i <= Integer.MAX_VALUE; i++) {
            customInfinispanCache.putInCache(String.format("%s-%s", clusterNodeIdentifier, i), String.format("value %s-%d", clusterNodeIdentifier, i));
            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readClusterData() {
        String clusterNodeIdentifier = UUID.randomUUID().toString();

        // Read data
        for (int i=1; ; i++) {
            int cacheSize = customInfinispanCache.getCache().keySet().size();
            logger.info("customInfinispanCache has {} keys", cacheSize);
            try {
                Thread.sleep(clusterReadSleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // dump full cache every so often
            if (i%10 == 0) {
                for (String key : customInfinispanCache.getCache().keySet()) {
                    logger.info("printing cache entry with key {} and value {}", key, customInfinispanCache.getCache().get(key));
                }
            }
        }
    }
}
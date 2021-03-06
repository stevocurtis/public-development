package com.fenixinfotech.infinispan.playpen;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class CustomInfinispanCache {
    private static final Logger logger = LoggerFactory.getLogger(CustomInfinispanCache.class);

    private EmbeddedCacheManager defaultCacheManager = null;
    Cache<String, String> cache = null;

    public CustomInfinispanCache() {
        if (defaultCacheManager == null) {
            try {
                defaultCacheManager = new DefaultCacheManager(getInfinispanConfig().toPath().toString());
            } catch (IOException e) {
                logger.error("issue retrieving infinispan config", e);
            }
            cache = defaultCacheManager.getCache();
        }
    }

    public Cache<String, String> getCache() {
        return cache;
    }

    public File getInfinispanConfig() {
        File infinispanConfigFile = new File(getClass().getClassLoader().getResource("infinispan.xml").getFile());
        return infinispanConfigFile;
    }

    public void putInCache(String key, String value) {
        logger.info("Storing in cache {} element with key {} and value {}", "default", key, value);
        getCache().put(key, value);
    }

    public Object getFromCache(String key) {
        logger.debug("Retrieving from cache element with key {}", key);
        Object value = getCache().get(key);

        logger.debug("Retrieving from cache {} element with key {} found value {}", "default", key, value);
        return value;
    }

    public void closeCache() {
        try {
            if (cache != null)
                cache.stop();
        } catch (Exception e) {
        } finally {

            if (defaultCacheManager != null)
                defaultCacheManager.stop();
        }
    }
}
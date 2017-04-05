package com.fenixinfotech.infinispan.playpen;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomInfinispanCache {
    private static final Logger logger = LoggerFactory.getLogger(CustomInfinispanCache.class);

    private EmbeddedCacheManager defaultCacheManager = null;
    Cache<String, String> cache = null;

    public CustomInfinispanCache() {
        if (defaultCacheManager == null) {
            defaultCacheManager = new DefaultCacheManager();
            cache = defaultCacheManager.getCache();
        }
    }

    public Cache<String, String> getCache() {
        return cache;
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
        if (cache != null)
            cache.stop();
    }
}
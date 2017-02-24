package com.fenixinfotech.infinispan.playpen;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomInfinispanCache {
    private static final Logger logger = LoggerFactory.getLogger(CustomInfinispanCache.class);

    private DefaultCacheManager defaultCacheManager;
    private Cache cache;

    public CustomInfinispanCache() {
        // Construct a simple local cache manager with default configuration
        this.defaultCacheManager = new DefaultCacheManager();
        // Obtain the default cache
        cache = this.defaultCacheManager.getCache();
    }

    public void putInCache(String key, String value) {
        logger.info("Storing in cache {} element with key {} and value {}", "default", key, value);
        cache.put(key, value);
    }

    public Object getFromCache(String key) {
        logger.debug("Retrieving from cache element with key {}", key);
        Object value = cache.get(key);

        logger.debug("Retrieving from cache {} element with key {} found value {}", "default", key, value);
        return value;
    }
}
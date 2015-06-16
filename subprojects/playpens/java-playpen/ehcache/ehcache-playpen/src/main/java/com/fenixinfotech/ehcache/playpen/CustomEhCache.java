package com.fenixinfotech.ehcache.playpen;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEhCache
{
    private static final Logger logger = LoggerFactory.getLogger(CustomEhCache.class);

    public CustomEhCache()
    {}

    public void putInCache(String cacheIdentifier, Object elementName, Object elementValue)
    {
        logger.info("Storing in cache {} element with name {} and value {}", cacheIdentifier, elementName, elementValue);

        if (cacheIdentifier != null && cacheIdentifier.length() > 0)
        {
            CacheManager cacheManager = CacheManager.getInstance();
            Cache cache = null;
            if (cacheManager.cacheExists(cacheIdentifier))
            {
                cache =  cacheManager.getCache(cacheIdentifier);
            }
            else
            {
                cacheManager.addCache(cacheIdentifier);
                cache =  cacheManager.getCache(cacheIdentifier);
            }
            cache.put(new Element(elementName, elementValue));
        }
    }

    public Object getFromCache(String cacheIdentifier, Object elementName)
    {
        logger.debug("Retrieving from cache {} element with name {}", cacheIdentifier, elementName);

        Object elementValue = null;
        if (cacheIdentifier != null && cacheIdentifier.length() > 0)
        {
            CacheManager cacheManager = CacheManager.getInstance();
            if (cacheManager.cacheExists(cacheIdentifier))
            {
                Cache cache =  cacheManager.getCache(cacheIdentifier);
                if (cache.isKeyInCache(elementName))
                {
                    elementValue = cache.get(elementName).getObjectValue();
                }
            }
        }

        logger.debug("Retrieving from cache {} element with name {} found value {}", cacheIdentifier, elementName, elementValue);
        return elementValue;
    }
}
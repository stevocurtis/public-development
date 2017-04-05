package com.fenixinfotech.infinispan.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        if (args.length > 0) {
            String port = args[0];
            logger.info("starting infinispan node on port {}", port);
            CustomInfinispanCache customInfinispanCache = new CustomInfinispanCache();
            customInfinispanCache.putInCache("key 1", "value 1");
        }

    }

}

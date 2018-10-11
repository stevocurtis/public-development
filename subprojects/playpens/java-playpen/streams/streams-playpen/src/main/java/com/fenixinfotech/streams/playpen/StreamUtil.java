package com.fenixinfotech.streams.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class StreamUtil {

    Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public long count(String[] input) {

        long count = 0;
        if (input != null) {
            count = Arrays.stream(input)
                    .count();
        }
        logger.info("count {}", count);
        return count;
    }

    public long countUnique(String[] input) {

        long count = 0;
        if (input != null) {
            count = Arrays.stream(input)
                    .count();
        }
        logger.info("count {}", count);
        return count;
    }

    public void toMap(String[] input) {

        long count = Arrays.stream(input)
                .count();
        logger.info("count {}", count);
    }
}

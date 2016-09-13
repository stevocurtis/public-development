package com.fenixinfotech.instrumentation.carrotsearch;

import com.carrotsearch.sizeof.RamUsageEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Memory
{
    private static final Logger logger = LoggerFactory.getLogger(Memory.class);

    public long getSizeof(Object object)
    {
        long size = RamUsageEstimator.sizeOfAll(object);
        logger.info("RamUsageEstimator sizeOfAll {} bytes", size);
        return size;
    }

    public String getHumanReadableSizeof(Object object)
    {
        String humanReadableSize = RamUsageEstimator.humanSizeOf(object);
        logger.info("RamUsageEstimator humanReadableSize {}", humanReadableSize);
        return humanReadableSize;
    }
}

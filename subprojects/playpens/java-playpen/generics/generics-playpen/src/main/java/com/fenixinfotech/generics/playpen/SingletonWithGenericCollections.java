package com.fenixinfotech.generics.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SingletonWithGenericCollections {

    Logger logger = LoggerFactory.getLogger(SingletonWithGenericCollections.class);
    private static SingletonWithGenericCollections INSTANCE = null;
    private Map<String, List<String>> data;

    public SingletonWithGenericCollections() {
        data = Collections.synchronizedMap(new HashMap<String, List<String>>());
    }

    public void addData(String dataId, String value) {
        logger.debug("adding dataId {} value {}", dataId, value);
        if (dataId != null) {
            if (data.containsKey(dataId)) {
                data.get(dataId).add(value);
            } else {
                data.put(dataId, new ArrayList<String>(Arrays.asList(value)));
            }
        }
    }

    public List<String> getData(String dataId) {
        List<String> retrievedData = data.get(dataId);
        logger.debug("dataId {} returning retrievedData {}", dataId, retrievedData);
        return retrievedData;
    }

    public static SingletonWithGenericCollections getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonWithGenericCollections.class) {
                INSTANCE = new SingletonWithGenericCollections();
            }
        }
        return INSTANCE;
    }
}
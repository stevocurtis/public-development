package com.fenixinfotech.generics.playpen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonWithGenericCollectionsTest {

    SingletonWithGenericCollections singletonWithGenericCollections;

    @Before
    public void init() {
        singletonWithGenericCollections = SingletonWithGenericCollections.getInstance();
        assertNotNull(singletonWithGenericCollections);
    }

    @Test
    public void testAdd() throws Exception {
        // collection 1
        singletonWithGenericCollections.addData("collection1", "collection1_value1");
        singletonWithGenericCollections.addData("collection1", "collection1_value2");
        singletonWithGenericCollections.addData("collection1", "collection1_value3");
        singletonWithGenericCollections.addData("collection1", "collection1_value4");
        singletonWithGenericCollections.addData("collection1", "collection1_value5");
        // collection 2
        singletonWithGenericCollections.addData("collection2", "collection2_value1");
        singletonWithGenericCollections.addData("collection2", "collection2_value2");

        // check content
        assertEquals(5, singletonWithGenericCollections.getData("collection1").size());
        assertEquals(2, singletonWithGenericCollections.getData("collection2").size());
    }


}
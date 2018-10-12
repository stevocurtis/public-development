package com.fenixinfotech.database.mongo;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class MongoAppTest {

    @Test
    @Ignore
    public void testGetDatabases() {
        Set<String> databases = new MongoApp().getDatabases();
        assertNotNull(databases);
        assertTrue(databases.size() > 0);
    }

}
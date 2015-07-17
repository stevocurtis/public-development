package com.fenixinfotech.exist.db.playpen;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ExistDbServerTest
{
    public static final String dbName = "testDb";
    public static final String dbConfLocation = "." + File.separator + "src" + File.separator + "test" + File.separator + "data" + File.separator + "test-exist-db-conf.xml";
    public File dbConfFile = new File(dbConfLocation);;

    @Test
    public void testStartEmbeddedDB()
    {
        assertNotNull(dbConfFile);
        assertTrue(dbConfFile.exists());

        ExistDbServer existDbServer = new ExistDbServer();
//        existDbServer.
    }
}
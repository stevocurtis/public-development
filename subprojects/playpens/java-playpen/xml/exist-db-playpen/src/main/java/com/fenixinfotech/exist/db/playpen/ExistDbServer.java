package com.fenixinfotech.exist.db.playpen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

public class ExistDbServer
{
    private static final Logger logger = LoggerFactory.getLogger(ExistDbServer.class);
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    final static String driver = "org.exist.xmldb.DatabaseImpl";

    public void startEmbeddedDB(String dbName, String confLocation) throws Exception
    {
        // Initialise database driver
        Class cl = Class.forName(driver);
        Database database = (Database)cl.newInstance();
        database.setProperty("create-database", "true");
        database.setProperty("configuration", confLocation);
        database.setProperty("database-id", dbName);
        DatabaseManager.registerDatabase(database);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, XMLDBException
    {
    }
}

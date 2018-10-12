package com.fenixinfotech.database.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class MongoApp {

    Logger logger = LoggerFactory.getLogger(MongoApp.class);
    String host;
    int port;
    private MongoClient mongoClient = null;

    public MongoApp() {
        this("localhost", 27017);
    }

    public MongoApp(String host, int port) {
        logger.info("connecting to mongo database on host {} port {}", host, port);
        this.host = host;
        this.port = port;
        mongoClient = new MongoClient(host, port);
    }

    public Set<String> getDatabases() {
        Set<String> databases = new TreeSet<>();
        mongoClient.listDatabaseNames().forEach((Consumer<? super String>) (v) -> databases.add(v));

        logger.info("found databases {}", databases);
        return databases;
    }

    public Set<String> getCollections(String databaseName) {
        return getCollections(databaseName, null, null);
    }

    public Set<String> getCollections(String databaseName, String username, String password) {
        Set<String> collections = new TreeSet<>();
        if (!isStringEmpty(databaseName)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
            if (!isStringEmpty(username) && !isStringEmpty(password)) {
                mongoDatabase.
            }
            mongoDatabase.listCollectionNames()
        }
        Set<String> databases = new TreeSet<>();
        mongoClient.listDatabaseNames().forEach((Consumer<? super String>) (v) -> databases.add(v));

        logger.info("found databases {}", databases);
        return databases;
    }

    private boolean isStringEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }
}
package com.potlucker.mongo;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.potlucker.properties.PropertiesLookup;

public class ConnectionProvider {
    /**
     * TODO:modify this method to allow passing the collection name to it
     * @return
     */

    public DBCollection getCollection(String collectionName) {
        try {
            PropertiesLookup pl = new PropertiesLookup();
            MongoClient mongo = new MongoClient(pl.getProperty("mongodbURL"),
                    Integer.parseInt(pl.getProperty("mongodbPORT")));

            DB db = mongo.getDB(pl.getProperty("mongodbDBNAME"));
            if (db == null) {
                System.out.println("Could not connect to DB");
            }

            boolean auth = db.authenticate(
                    pl.getProperty("mongodbUSER"),
                    pl.getProperty("mongodbPW").toCharArray() );
            if (auth == false) {
                System.out.println("Could not authenticate");
            }

            DBCollection namedColl = db.getCollection(collectionName);
            return namedColl;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;

    }


}

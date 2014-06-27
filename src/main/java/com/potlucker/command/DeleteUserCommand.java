package com.potlucker.command;

import com.potlucker.mongo.ConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;


/**
 * Created by treznick on 6/26/14.
 */
public class DeleteUserCommand {
    public boolean execute(String key) {
        ConnectionProvider conn = new ConnectionProvider();
        DBCollection usersColl = conn.getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("key", key);
        DBCursor cursor = usersColl.find(searchQuery);

        while(cursor.hasNext()) {
            usersColl.remove(searchQuery);
        }
        return true;
    }
}

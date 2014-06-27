package com.potlucker.command;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.ObjectMapper;

import com.potlucker.model.User;
import com.potlucker.mongo.ConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Created by treznick on 6/27/14.
 */
public class GetUserCommand {
    ObjectMapper mapper = new ObjectMapper();

    public User execute(String key, String value) {
        ConnectionProvider conn = new ConnectionProvider();
        DBCollection usersCollection = conn.getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        if (key.equals("_id")) {
            searchQuery.put(key, new ObjectId(value));
        } else {
            searchQuery.put(key,value);
        }

        DBObject user = usersCollection.findOne(searchQuery);

        User vUser = null;

        try {
            vUser = mapper.readValue(user.toString(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vUser;
    }

    public static void main(String[] args) {
        GetUserCommand command = new GetUserCommand();

        User u = command.execute("key", "26dd6014-8e06-4069-91dc-056ccf9182b8");
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(u));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

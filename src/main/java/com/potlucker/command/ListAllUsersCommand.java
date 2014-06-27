package com.potlucker.command;

import java.util.ArrayList;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.potlucker.model.User;
import com.potlucker.mongo.ConnectionProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by treznick on 6/27/14.
 */
public class ListAllUsersCommand {

    public ArrayList<User> execute() {
        ConnectionProvider conn = new ConnectionProvider();
        DBCollection usersCollection = conn.getCollection("users");

        DBCursor cursor = usersCollection.find();

        ArrayList<User> users = new ArrayList<User>();
        GetUserCommand getUser = new GetUserCommand();
        try {
            while (cursor.hasNext()) {
                User u = getUser.execute("_id",cursor.next().get("_id").toString());
                users.add(u);
            }
        } finally {
            cursor.close();
        }
        return users;
    }

}

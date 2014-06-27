package com.potlucker.command;

import org.codehaus.jackson.map.ObjectMapper;
import com.potlucker.mongo.ConnectionProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.potlucker.model.User;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by treznick on 6/26/14.
 */
public class CreateUserCommand {
    public boolean execute(User user) {
        ConnectionProvider conn = new ConnectionProvider();
        DBCollection collection = conn.getCollection("users");

        ObjectMapper mapper = new ObjectMapper();
        try {
            DBObject dbObject = (DBObject) JSON.parse(mapper.writeValueAsString(user));
            collection.insert(dbObject);
        } catch (Exception e) {
            System.out.println("Error during mapping of user to Mongo Object");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        CreateUserCommand create = new CreateUserCommand();
        User user = new User();
        user.setName("John Doe");
        ArrayList<String>pantry = new ArrayList<String>();
        pantry.add("eggs");
        pantry.add("milk");
        pantry.add("cream");
        user.setPantry(pantry);
        user.setKey(UUID.randomUUID().toString());

        if (create.execute(user)) {
            System.out.println("Success: User Created!");
        } else {
            System.out.println("Error: failed to create user");
        }

    }
}

package com.potlucker.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.potlucker.command.GetUserCommand;
import com.potlucker.command.CreateUserCommand;
import com.potlucker.command.ListAllUsersCommand;
import com.potlucker.command.DeleteUserCommand;
import com.potlucker.model.User;

import static java.util.UUID.randomUUID;

/**
 * Created by treznick on 6/25/14.
 */

@Path("users")
public class UserService {
    ObjectMapper mapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        ListAllUsersCommand listUsers = new ListAllUsersCommand();
        ArrayList<User> list = listUsers.execute();
        String usersString = null;
        try {
            usersString = mapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(usersString).build();
    }

    @GET
    @Path("/{key}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByValue(@PathParam("key") String key, @PathParam("value") String value) {
        GetUserCommand getUserCommand = new GetUserCommand();
        User user = getUserCommand.execute(key, value);
        String userString = null;
        try {
            userString = mapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(userString).build();
    }

    @POST
    @Path("/create")
    @Consumes("application/x-www-form-urlencoded")
    @Produces (MediaType.APPLICATION_JSON)
    public Response createUser(@FormParam("name") String name, @FormParam("pantry") String pantry) {

        try {
            CreateUserCommand create = new CreateUserCommand();
            User user = new User();
            user.setName(name);
            List<String> pantryArray = new ArrayList<String>(Arrays.asList(pantry.split(";")));
            user.setPantry((ArrayList<String>) pantryArray);
            user.setKey(randomUUID().toString());
            boolean success = create.execute(user);
            if (success) {
                return Response.status(201).entity("true").build();
            } else {
                return Response.status(500).entity("false").build();
            }
        } catch (Exception e) {
            return Response.status(500).entity(e.toString()).build();
        }
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String id) {
        DeleteUserCommand delete = new DeleteUserCommand();
        if(delete.execute(id)){
            return Response.status(200).entity(id).build();
        } else {
            return Response.status(500).entity("Error: Could not delete "+id).build();
        }
    }
}

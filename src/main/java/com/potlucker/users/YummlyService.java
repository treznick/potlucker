package com.potlucker.users;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import javax.ws.rs.PathParam;
import com.potlucker.command.GetRecipesCommand;

/**
 * Created by treznick on 6/27/14.
 */
@Path("recipes")
public class YummlyService {
    @GET
    @Path("/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes(@PathParam("value") String ingredientString ) {
        GetRecipesCommand get = new GetRecipesCommand();
        String responseString = get.execute(ingredientString);
        return Response.status(200).entity(responseString).build();
    }

}

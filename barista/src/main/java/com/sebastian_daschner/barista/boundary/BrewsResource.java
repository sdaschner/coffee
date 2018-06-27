package com.sebastian_daschner.barista.boundary;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("brews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrewsResource {

    @Inject
    CoffeeBrews coffeeBrews;

    @POST
    public Response startCoffeeBrew(JsonObject jsonObject) {
        String coffeeType = jsonObject.getString("type", null);

        if (coffeeType == null)
            throw new BadRequestException();

        coffeeBrews.startBrew(coffeeType);

        return Response.accepted().build();
    }

}

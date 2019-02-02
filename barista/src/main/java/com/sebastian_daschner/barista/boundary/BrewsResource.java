package com.sebastian_daschner.barista.boundary;

import com.sebastian_daschner.barista.entity.CoffeeBrew;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/brews/{id}")
public class BrewsResource {

    @Inject
    CoffeeBrews coffeeBrews;

    @PathParam("id")
    String id;

    @GET
    public Response retrieveCoffeeBrew() {
        CoffeeBrew brew = coffeeBrews.retrieveBrew(id);

        if (brew == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(buildResponse(brew)).build();
    }

    @PUT
    public Response updateCoffeeBrew(JsonObject jsonObject) {
        String coffeeType = jsonObject.getString("type", null);

        if (coffeeType == null)
            throw new BadRequestException();

        CoffeeBrew brew = coffeeBrews.startBrew(id, coffeeType);

        return Response.status(Response.Status.CREATED)
                .entity(buildResponse(brew))
                .build();
    }

    private JsonObject buildResponse(CoffeeBrew brew) {
        return Json.createObjectBuilder()
                .add("status", brew.getStatus())
                .add("type", brew.getType())
                .add("location", brew.getLocation())
                .build();
    }

}

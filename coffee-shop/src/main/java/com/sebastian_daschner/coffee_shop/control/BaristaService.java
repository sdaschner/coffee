package com.sebastian_daschner.coffee_shop.control;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("brews/{id}")
@RegisterRestClient
public interface BaristaService {

    @PUT
    JsonObject brew(@PathParam("id") String id, JsonObject jsonObject);

    @GET
    JsonObject readStatus(@PathParam("id") String id);

}

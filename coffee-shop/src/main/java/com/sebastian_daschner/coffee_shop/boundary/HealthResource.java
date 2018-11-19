package com.sebastian_daschner.coffee_shop.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("health")
public class HealthResource {

    @Inject
    @ConfigProperty(name = "development", defaultValue = "false")
    boolean developmentMode;

    @GET
    public Response health() {
        return Response.ok("OK").header("X-Dev-Mode", developmentMode).build();
    }

}

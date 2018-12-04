package com.sebastian_daschner.coffee_shop.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("health")
public class HealthResource {

    @Inject
    @ConfigProperty(name = "version", defaultValue = "N/A")
    String appServerVersion;

    @GET
    public Response health() {
        return Response.ok()
                .entity("OK")
                .header("Open-Liberty-Version", appServerVersion)
                .build();
    }

}

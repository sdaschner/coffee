package com.sebastian_daschner.coffee_shop.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("health")
public class HealthResource {

    @GET
    public Response health() {
        return Response.ok("OK")
                .header("App-Version", "2")
                .build();
    }

}

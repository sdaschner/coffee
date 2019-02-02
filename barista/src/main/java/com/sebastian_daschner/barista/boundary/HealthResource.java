package com.sebastian_daschner.barista.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthResource {

    @GET
    public String health() {
        return "OK";
    }

}

package com.sebastian_daschner.coffee_shop;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.CompletionException;

@Provider
public class RejectedExecutionHandler implements ExceptionMapper<CompletionException> {

    @Override
    public Response toResponse(CompletionException exception) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }
}

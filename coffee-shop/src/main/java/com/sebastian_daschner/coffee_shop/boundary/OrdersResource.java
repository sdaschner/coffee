package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.UUID;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @GET
    public JsonArray getOrders() {
        return coffeeShop.getOrders().stream()
                .map(this::buildOrder)
                .collect(JsonCollectors.toJsonArray());
    }

    private JsonObject buildOrder(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name().toLowerCase())
                .add("status", order.getStatus().name())
                .add("_self", buildUri(order).toString())
                .build();
    }

    @GET
    @Path("{id}")
    public CoffeeOrder getOrder(@PathParam("id") UUID id) {
        return coffeeShop.getOrder(id);
    }

    @POST
    public Response orderCoffee(@Valid @NotNull CoffeeOrder order) {
        final CoffeeOrder storedOrder = coffeeShop.orderCoffee(order);
        return Response.created(buildUri(storedOrder)).build();
    }

    private URI buildUri(CoffeeOrder order) {
        return uriInfo.getBaseUriBuilder()
                .host(request.getServerName())
                .port(request.getServerPort())
                .path(OrdersResource.class)
                .path(OrdersResource.class, "getOrder")
                .build(order.getId());
    }
}

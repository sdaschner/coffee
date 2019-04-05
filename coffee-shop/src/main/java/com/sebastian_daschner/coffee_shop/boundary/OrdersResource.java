package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @GET
    public CompletionStage<JsonArray> getOrders() {
        return coffeeShop.getOrders()
                .thenApply(o -> o.stream()
                        .map(this::buildOrder)
                        .collect(JsonCollectors.toJsonArray()));
    }

    private JsonObject buildOrder(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name())
                .add("status", order.getStatus().name())
                .add("id", order.getId().toString())
                .build();
    }

    @GET
    @Path("{id}")
    public CompletionStage<CoffeeOrder> getOrder(@PathParam("id") UUID id) {
        return coffeeShop.getOrder(id);
    }

    @POST
    public CompletionStage<Response> orderCoffee(@Valid @NotNull CoffeeOrder order) {
        return coffeeShop.orderCoffee(order)
                .thenApply(o -> Response.noContent().build())
                .exceptionally(e -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header("X-Error", e.getMessage())
                        .build());
    }

}

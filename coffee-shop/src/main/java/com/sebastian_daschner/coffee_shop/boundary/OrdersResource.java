package com.sebastian_daschner.coffee_shop.boundary;

import com.airhacks.porcupine.execution.boundary.Dedicated;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Inject
    @Dedicated("coffee-read")
    ExecutorService readExecutor;

    @Inject
    @Dedicated("coffee-order")
    ExecutorService writeExecutor;

    @GET
    public CompletionStage<JsonArray> getOrders() {
        return CompletableFuture.supplyAsync(() -> coffeeShop.getOrders().stream()
                .map(this::buildOrder)
                .collect(JsonCollectors.toJsonArray()), readExecutor);
    }

    private JsonObject buildOrder(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name())
                .add("status", order.getStatus().name())
                .add("_self", buildUri(order).toString())
                .build();
    }

    @GET
    @Path("{id}")
    public CompletionStage<CoffeeOrder> getOrder(@PathParam("id") UUID id) {
        return CompletableFuture.supplyAsync(() -> coffeeShop.getOrder(id), readExecutor);
    }

    @POST
    public CompletionStage<Response> orderCoffee(@Valid @NotNull CoffeeOrder order) {
        return CompletableFuture.supplyAsync(() -> coffeeShop.orderCoffee(order), writeExecutor)
                .thenApply(o -> Response.created(buildUri(o)).build())
                .exceptionally(e -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header("X-Error", e.getMessage())
                        .build());
    }

    private URI buildUri(CoffeeOrder order) {
        return uriInfo.getBaseUriBuilder()
                .host(request.getServerName())
                .port(-1)
                .path(OrdersResource.class)
                .path(OrdersResource.class, "getOrder")
                .build(order.getId());
    }

}

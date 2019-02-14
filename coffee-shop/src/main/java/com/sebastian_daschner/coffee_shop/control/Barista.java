package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class Barista {

    private Client client;
    private WebTarget target;

    @PostConstruct
    private void initClient() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        target = client.target("http://barista:9080/barista/resources/brews/{id}");
    }

    public OrderStatus brewCoffee(CoffeeOrder order) {
        JsonObject requestBody = createRequestBody(order);
        Response response = sendBrewRequest(requestBody, order.getId().toString());
        return readStatus(response);
    }

    private JsonObject createRequestBody(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name().toLowerCase())
                .build();
    }

    private Response sendBrewRequest(JsonObject requestBody, String id) {
        try {
            return target.resolveTemplate("id", id)
                    .request()
                    .put(Entity.json(requestBody));
        } catch (Exception e) {
            throw new IllegalStateException("Could not retrieve brew status, reason: " + e.getMessage(), e);
        }
    }

    public OrderStatus retrieveBrewStatus(CoffeeOrder order) {
        Response response = getBrewStatus(order.getId().toString());
        return readStatus(response);
    }

    private Response getBrewStatus(String id) {
        return target.resolveTemplate("id", id)
                .request().get();
    }

    private OrderStatus readStatus(Response response) {
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new IllegalStateException("Could not retrieve brew status, response: " + response.getStatus());

        JsonObject entity = response.readEntity(JsonObject.class);
        final OrderStatus status = OrderStatus.fromString(entity.getString("status", null));

        if (status == null)
            throw new RuntimeException("Could not read known status from response: " + entity);

        return status;
    }

    @PreDestroy
    private void closeClient() {
        client.close();
    }

}

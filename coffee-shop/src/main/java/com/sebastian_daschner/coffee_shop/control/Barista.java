package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

@ApplicationScoped
public class Barista {

    @Inject
    @RestClient
    BaristaService baristaService;

    public OrderStatus brewCoffee(CoffeeOrder order) {
        JsonObject requestBody = createRequestBody(order);
        JsonObject responseBody = baristaService.brew(order.getId().toString(), requestBody);
        return readStatus(responseBody);
    }

    private JsonObject createRequestBody(CoffeeOrder order) {
        return Json.createObjectBuilder()
                .add("type", order.getType().name().toLowerCase())
                .build();
    }

    public OrderStatus retrieveBrewStatus(CoffeeOrder order) {
        JsonObject response = baristaService.readStatus(order.getId().toString());
        return readStatus(response);
    }

    private OrderStatus readStatus(JsonObject entity) {
        final OrderStatus status = OrderStatus.fromString(entity.getString("status", null));

        if (status == null)
            throw new RuntimeException("Could not read known status from response: " + entity);

        return status;
    }

}

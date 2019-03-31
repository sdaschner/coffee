package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.control.Barista;
import com.sebastian_daschner.coffee_shop.control.Orders;
import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class CoffeeShop {

    @Inject
    Orders orders;

    @Inject
    Barista barista;

    @Bulkhead(value = 4, waitingTaskQueue = 4)
    @Asynchronous
    public CompletionStage<List<CoffeeOrder>> getOrders() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(orders.retrieveAll());
    }

    @Bulkhead(value = 4, waitingTaskQueue = 4)
    @Asynchronous
    public CompletionStage<CoffeeOrder> getOrder(UUID id) {
        return CompletableFuture.completedFuture(orders.retrieve(id));
    }

    @Bulkhead(value = 4, waitingTaskQueue = 8)
    @Asynchronous
    public CompletionStage<CoffeeOrder> orderCoffee(CoffeeOrder order) {

        barista.startCoffeeBrew(order.getType());

        order.setId(UUID.randomUUID());
        orders.store(order.getId(), order);
        return CompletableFuture.completedFuture(order);
    }

}

package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.control.Barista;
import com.sebastian_daschner.coffee_shop.control.Orders;
import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CoffeeShop {

    @Inject
    Orders orders;

    @Inject
    Barista barista;

    public List<CoffeeOrder> getOrders() {
        return orders.retrieveAll();
    }

    public CoffeeOrder getOrder(UUID id) {
        return orders.retrieve(id);
    }

    @Counted(monotonic = true)
    public CoffeeOrder orderCoffee(CoffeeOrder order) {

        barista.startCoffeeBrew(order.getType());

        order.setId(UUID.randomUUID());
        orders.store(order.getId(), order);
        return order;
    }

}

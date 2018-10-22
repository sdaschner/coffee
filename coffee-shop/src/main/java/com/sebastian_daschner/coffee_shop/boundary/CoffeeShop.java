package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.control.Barista;
import com.sebastian_daschner.coffee_shop.control.Orders;
import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Stateless
public class CoffeeShop {

    @Inject
    Orders orders;

    @Inject
    Barista barista;

    public List<CoffeeOrder> getOrders() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return orders.retrieveAll();
    }

    public CoffeeOrder getOrder(UUID id) {
        return orders.retrieve(id);
    }

    public CoffeeOrder orderCoffee(CoffeeOrder order) {

        barista.startCoffeeBrew(order.getType());

        order.setId(UUID.randomUUID());
        orders.store(order.getId(), order);
        return order;
    }

}

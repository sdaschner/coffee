package com.sebastian_daschner.coffee_shop.boundary;

import com.sebastian_daschner.coffee_shop.control.Barista;
import com.sebastian_daschner.coffee_shop.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.control.Orders;
import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import com.sebastian_daschner.coffee_shop.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

    @Inject
    OrderProcessor orderProcessor;

    @Inject
    @ConfigProperty(name = "coffeeShop.order.defaultCoffeeType", defaultValue = "ESPRESSO")
    private CoffeeType defaultCoffeeType;

    public List<CoffeeOrder> getOrders() {
        return orders.retrieveAll();
    }

    public CoffeeOrder getOrder(UUID id) {
        return orders.retrieve(id);
    }

    public CoffeeOrder orderCoffee(CoffeeOrder order) {
        setDefaultType(order);
        OrderStatus status = barista.brewCoffee(order);
        order.setStatus(status);

        orders.store(order.getId(), order);
        return order;
    }

    private void setDefaultType(CoffeeOrder order) {
        if (order.getType() == null)
            order.setType(defaultCoffeeType);
    }

    public void processUnfinishedOrders() {
        orders.getUnfinishedOrders().forEach(orderProcessor::processOrder);
    }

}

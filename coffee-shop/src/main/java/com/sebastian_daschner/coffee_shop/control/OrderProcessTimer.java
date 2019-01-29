package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.boundary.CoffeeShop;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class OrderProcessTimer {

    @Inject
    CoffeeShop coffeeShop;

    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
    public void processOrder() {
        coffeeShop.processUnfinishedOrders();
    }

}

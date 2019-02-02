package com.sebastian_daschner.barista.boundary;

import com.sebastian_daschner.barista.control.RandomStatusProcessor;
import com.sebastian_daschner.barista.entity.CoffeeBrew;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CoffeeBrews {

    private final Map<String, CoffeeBrew> coffeeBrews = new ConcurrentHashMap<>();

    @Inject
    RandomStatusProcessor statusProcessor;

    public CoffeeBrew startBrew(String id, String coffeeType) {
        System.out.println("starting to brew: " + coffeeType);

        CoffeeBrew brew = new CoffeeBrew(coffeeType, "home");
        coffeeBrews.put(id, brew);

        return brew;
    }

    public CoffeeBrew retrieveBrew(String id) {
        System.out.println("retrieving brew: " + id);

        CoffeeBrew brew = coffeeBrews.get(id);

        if (brew == null)
            return null;

        brew.setStatus(statusProcessor.processStatus(brew));

        return brew;
    }

}

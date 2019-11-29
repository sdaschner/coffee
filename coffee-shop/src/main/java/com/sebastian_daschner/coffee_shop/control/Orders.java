package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.coffee_shop.entity.CoffeeOrder;
import com.sebastian_daschner.coffee_shop.entity.OrderStatus;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class Orders {

    private final ConcurrentHashMap<UUID, CoffeeOrder> orders = new ConcurrentHashMap<>();

    public List<CoffeeOrder> retrieveAll() {
        return orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public CoffeeOrder retrieve(UUID id) {
        return orders.get(id);
    }

    public void store(UUID id, CoffeeOrder order) {
        orders.put(id, order);
    }

    public List<CoffeeOrder> getUnfinishedOrders() {
        return orders.values().stream()
                .filter(o -> o.getStatus() != OrderStatus.COLLECTED)
                .collect(Collectors.toList());
    }

}

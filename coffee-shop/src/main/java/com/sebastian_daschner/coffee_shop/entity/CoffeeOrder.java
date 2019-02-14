package com.sebastian_daschner.coffee_shop.entity;

import com.sebastian_daschner.coffee_shop.BrewLocationDeserializer;
import com.sebastian_daschner.coffee_shop.CoffeeTypeDeserializer;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CoffeeOrder {

    @JsonbTransient
    private final UUID id = UUID.randomUUID();

    @NotNull
    @JsonbTypeAdapter(CoffeeTypeDeserializer.class)
    private CoffeeType type;

    private OrderStatus status;

    @JsonbTypeAdapter(BrewLocationDeserializer.class)
    private BrewLocation location;

    public UUID getId() {
        return id;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BrewLocation getLocation() {
        return location;
    }

    public void setLocation(BrewLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", location=" + location +
                '}';
    }
}

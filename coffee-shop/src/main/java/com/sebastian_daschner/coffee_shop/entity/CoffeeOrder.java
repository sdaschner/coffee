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

    public UUID getId() {
        return id;
    }

    @NotNull
    @JsonbTypeAdapter(CoffeeTypeDeserializer.class)
    private CoffeeType type;

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    @JsonbTypeAdapter(BrewLocationDeserializer.class)
    private BrewLocation location;

    public BrewLocation getLocation() {
        return location;
    }

    public void setLocation(BrewLocation location) {
        this.location = location;
    }



    private OrderStatus status;




    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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

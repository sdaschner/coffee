package com.sebastian_daschner.coffee_shop;


import com.sebastian_daschner.coffee_shop.entity.BrewLocation;


import javax.json.bind.adapter.JsonbAdapter;

public class BrewLocationDeserializer implements JsonbAdapter<BrewLocation, String> {

    @Override
    public String adaptToJson(BrewLocation type) {
        return type.name();
    }

    @Override
    public BrewLocation adaptFromJson(String type) {
        return BrewLocation.fromString(type);
    }
}

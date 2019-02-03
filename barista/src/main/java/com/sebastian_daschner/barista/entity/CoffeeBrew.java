package com.sebastian_daschner.barista.entity;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
public class CoffeeBrew {

    @Inject @ConfigProperty(name="coffeebrew.status", defaultValue =  "Preparing") String status;

    private String type;

    public CoffeeBrew() {
    }

    public CoffeeBrew(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

package com.sebastian_daschner.barista.entity;

public class CoffeeBrew {

    private String status = "PREPARING";
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

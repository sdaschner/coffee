package com.sebastian_daschner.barista.entity;

public class CoffeeBrew {

    private String status = "PREPARING";
    private String type;
    private String location;

    public CoffeeBrew() {
    }

    public CoffeeBrew(String type, String location) {
        this.type = type;
        this.location = location;
    }

    public void setLocation(String location) {this.location= location;}
    public String getLocation() {return this.location;}

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

package com.restfullbooker.config;

public enum Endpoint {
    CreateBooking("https://restful-booker.herokuapp.com/booking");

    private final String resource;

    Endpoint(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}

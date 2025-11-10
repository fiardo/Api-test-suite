package com.restfullbooker.tests.resources;

public enum Endpoint {
    Auth("https://restful-booker.herokuapp.com/auth"),
    CreateBooking("https://restful-booker.herokuapp.com/booking"),
    DeleteBooking("https://restful-booker.herokuapp.com/booking/");



    private final String resource;

    Endpoint(String resource) {
        this.resource = resource;
    }

    public  String getResource() {
        return resource;
    }
}

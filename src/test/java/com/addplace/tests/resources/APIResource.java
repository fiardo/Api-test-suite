package com.addplace.tests.resources;

public enum APIResource {

    AddPlaceAPI("https://rahulshettyacademy.com/maps/api/place/add/json"),
    getPlaceAPI("https://rahulshettyacademy.com/maps/api/place/get/json"),
    deletePlaceAPI("https://rahulshettyacademy.com/maps/api/place/delete/json");

    private final String resource;

    APIResource(String resource) {
        this.resource = resource;
    }

    public  String getResource() {
        return resource;
    }


}

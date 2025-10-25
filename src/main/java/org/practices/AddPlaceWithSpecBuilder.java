package org.practices;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoBddFramework.AddPlace;
import pojoBddFramework.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlaceWithSpecBuilder {

    public static void main(String[] arg)
    {

        //RestAssured.baseURI = "https://rahulshettyacademy.com/";

       RequestSpecification baseresponse = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addQueryParam("key" , "qaclick123")
                .setContentType(ContentType.JSON).build();


        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setPhone_number("(+91) 983 893 3937");
        p.setName("Frontline house");
        p.setLanguage("French-IN");
        p.setWebsite( "http://google.com");

        List<String> myList =new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);


            ResponseSpecification responsespces = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
            RequestSpecification response = given().spec(baseresponse).body(p);

                Response requestsent = response.when().post("/maps/api/place/add/json").
                        then().spec(responsespces).extract().response();

                        String getresponse = requestsent.asString();
                        System.out.println("Add Place Api Response : " + getresponse);


    }

}


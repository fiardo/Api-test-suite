package com.addplace.practices;

import io.restassured.RestAssured;
import com.addplace.pojoBddFramework.AddPlace;
import com.addplace.pojoBddFramework.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddPlaceWithPojoSerialization {

    public static void main(String[] arg)
    {

        RestAssured.baseURI = "https://rahulshettyacademy.com/";

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



        String response = given().log().all()
                .queryParam("key" , "qaclick123")
                .header("Content-Type","application/json")
                .body(p).when()
                .post("/maps/api/place/add/json").
                then()
                .assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .extract().response().asString();
        System.out.println("Add Place Api Response : " + response);


    }

}

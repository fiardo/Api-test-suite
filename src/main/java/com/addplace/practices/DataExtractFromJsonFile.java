package com.addplace.practices;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import com.addplace.resource.ReUsableMethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DataExtractFromJsonFile {

        public static void main(String[] args) throws IOException {
            //given - all input details
            //when - submit the api - resource , http method
            //then - validate the response
            //content of the file to string -- content of file can convert into bytes--bytes data to string

            RestAssured.baseURI="https://rahulshettyacademy.com";

            String response = given().log().all()
                    .queryParam("key" , "qaclick123")
                    .header("Content-Type","application/json")
                    .body(new String(Files.readAllBytes(Paths.get("D:\\addplace.json")))). // this line takes input from json file
                    when()
                    .post("/maps/api/place/add/json").
                    then()
                    .assertThat().statusCode(200)
                    .body("scope",equalTo("APP"))
                    .extract().response().asString();
            System.out.println("Add Place Api Response : " + response);

            // extract place_id from response

            JsonPath js = new JsonPath(response);
            String placeId = js.getString("place_id");
            System.out.println("Place ID : " + placeId);

            //update place
            String newAddress = "Summer Walk, Africa";
            given().log().all()
                    .queryParam("key" , "qaclick123")
                    .header("Content-Type","application/json")
                    .body("{\n" +
                            "\"place_id\":\""+placeId+"\",\n" +
                            "\"address\":\""+newAddress+"\",\n" +
                            "\"key\":\"qaclick123\"\n" +
                            "}").
                    when()
                    .put("/maps/api/place/update/json").
                    then().log().all()
                    .assertThat().statusCode(200)
                    .body("msg",equalTo("Address successfully updated"));

            //get place to validate if new address is present in response
            String getPlaceResponse = given().log().all()
                    .queryParam("key" , "qaclick123")
                    .queryParam("place_id" , placeId).
                    when()
                    .get("/maps/api/place/get/json").
                    then().log().all()
                    .assertThat().statusCode(200)
                    .extract().response().asString();


            JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
            System.out.println("Get Place Api Response : " + getPlaceResponse);
            String actualAddress = js1.getString("address");
            System.out.println("Actual Address : " + actualAddress);
            Assert.assertEquals(actualAddress, newAddress);

        }
}



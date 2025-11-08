package com.restfullbooker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class e2ewithoutframework {

    public static void main(String[] args) {

        //given - all input details
        //when - submit the api - resource , http method
        //then - validate the response


        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        //Authentication
        String response = given()
                .header("Content-Type", "application/json")
                .header("Host", "restful-booker.herokuapp.com")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().post("/auth")
                .then()
                .statusCode(200).extract().asString();

        JsonPath js = new JsonPath(response);
        String token = js.getString("token");
        System.out.println("Token : " + token);

        //Get Bookingids
        given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .and()
                .body("bookingid", not(empty()))
                .extract().response().asString();

        //Create Booking
        String CreateBooking = given().
                header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Tim\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().post("/booking")
                .then().log().all()
                .statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(CreateBooking);
        String bookingId = js1.getString("bookingid");
        System.out.println("Booking ID : " + bookingId);

        //Get Booking

        given()
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then().log().all()
                .statusCode(200)
                .and()
                .body("firstname", equalTo("Tim"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2018-01-01"))
                .body("bookingdates.checkout", equalTo("2019-01-01"))
                .body("additionalneeds", equalTo("Breakfast"))
                .extract().response().asString();

        //Update Booking
        String UpdateBooking = given()
                .pathParam("bookingId", bookingId)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body("{\n" +
                        "    \"firstname\" : \"james\",\n" +
                        "    \"lastname\" : \"garg\"\n" +
                        "}")
                .when().patch("/booking/{bookingId}")
                .then().log().all()
                .statusCode(200).extract().response().asString();

        //Delete Booking

        given()
                .pathParam("bookingId", bookingId)
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/{bookingId}")
                .then()
                .statusCode(201);
        System.out.println("Booking Deleted Successfully with BookingId = " + bookingId);
    }
}

package com.addplace.practices;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import com.addplace.resource.ReUsableMethods;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EcommerceApiTest {

    public static void main(String[] args)

    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //***************************Login*******************************************************

        String loginresponse = given()
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\n" +
                        "\"userEmail\":\"pravinkumargarg@gmail.com\",\n" +
                        "\"userPassword\":\"Forget@420\"\n" +
                        "\n" +
                        "}")
                .when()
                .post("/api/ecom/auth/login")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("message", equalTo("Login Successfully"))
                .extract()
                .response()
                .asString();
        //System.out.println("Response after LoggedIn  : " + loginresponse);

        JsonPath js = ReUsableMethods.rawToJson(loginresponse);
        String token = js.getString("token");
        String userId = js.getString("userId");
        //System.out.println("Authentication Token is : " + token);

    //*************************// Creat Product // *********************************************************
       String productresponse =  given()
                .header("Content-Type","multipart/form-data")
                .header("Authorization",token)
                .formParam("productName", "Aqwerty2")
                .formParam("productAddedBy", userId)
                .formParam("productCategory", "fashion")
                .formParam("productSubCategory", "shirts")
                .formParam("productPrice", "11500")
                .formParam("productDescription", "Addias Originals")
                .formParam("productFor", "women")
                .multiPart("productImage",new File("C:\\Users\\pravin.garg\\Downloads\\img.png"))
                .when()
                .post("/api/ecom/product/add-product")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .body("message" , equalTo("Product Added Successfully"))
                .extract()
                .response().asString();
        //System.out.println("Response after product Added  : " + productresponse);

        JsonPath js1 = ReUsableMethods.rawToJson(productresponse);
        String productId = js1.getString("productId");

        //**************************** // Create Order //*********************************************************


       given()
               .header("Content-Type","application/json")
               .header("Authorization",token)
               .body("{\n" +
                       "  \"orders\": [\n" +
                       "    {\n" +
                       "      \"country\": \"India\",\n" +
                       "      \"productOrderedId\": \"" + productId + "\"\n" +
                       "    }\n" +
                       "  ]\n" +
                       "}")
               .when()
               .post("/api/ecom/order/create-order")
               .then().log().all()
               .assertThat()
               .statusCode(201)
               .body("message",equalTo("Order Placed Successfully"))
               .extract()
               .response()
               .asString();



       // ************************ Delete *********************************************************


        given()
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .delete("/api/ecom/product/delete-product/" + productId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("message",equalTo("Product Deleted Successfully"));


    }
}

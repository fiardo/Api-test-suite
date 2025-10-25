package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

    public RequestSpecification baseresponse;


    public RequestSpecification baseResponse() {

        baseresponse = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addQueryParam("key" , "qaclick123")
                .setContentType(ContentType.JSON).build();
        return baseresponse;

    }


}

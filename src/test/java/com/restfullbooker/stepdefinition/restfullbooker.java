package com.restfullbooker.stepdefinition;

import com.restfullbooker.tests.resources.Endpoint;
import com.restfullbooker.tests.resources.TestDataBuild;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import static com.restfullbooker.utils.utils.baseRequest;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class restfullbooker {

    RequestSpecification response;
    Response requestsent;
    ResponseSpecification responsespces;
    TestDataBuild data = new TestDataBuild();
    String bookingId;
    
    @Given("user creates a booking with the following details {string} {string} {string} {string} {string} {string} {string}")
    public void user_creates_a_booking_with_the_following_details(
            String firstname, String lastname, String totalpriceStr, String depositpaidStr,
            String checkin, String checkout, String additionalneeds) throws IOException {

        int totalprice = Integer.parseInt(totalpriceStr);
        boolean depositpaid = Boolean.parseBoolean(depositpaidStr);

        response = given().spec(baseRequest()).body(data.creatBookingPayload(firstname, lastname, totalprice,
                        depositpaid, checkin, checkout, additionalneeds));
    }

    @When("User calls {string} with {string} request")
    public void user_calls_with_post_request(String resource , String method) {
        // Write code here that turns the phrase above into concrete actions
        Endpoint endpoint = Endpoint.valueOf(resource);
        responsespces = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST"))
            requestsent = response.when().post(endpoint.getResource());
        else if (method.equalsIgnoreCase("GET"))
            requestsent = response.when().get(endpoint.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            requestsent = response.when().delete(endpoint.getResource());


        System.out.println("========== Response Body ==========");
        System.out.println(requestsent.asPrettyString());
        System.out.println("===================================");

        requestsent.then().spec(responsespces).extract().response();

    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode, (Integer) requestsent.getStatusCode());
    }

    @Then("store {string} from response body")
    public void store_from_response_body(String key) {
        // Write code here that turns the phrase above into concrete actions
        String responseString = requestsent.asString();
        JsonPath js = new JsonPath(responseString);
        bookingId = js.get(key).toString();
        System.out.println("BookingID: " + bookingId);
        // Store the value in a class variable or test context for later use
        // For example: testContext.setContext(key, js.get(key));
    }

}

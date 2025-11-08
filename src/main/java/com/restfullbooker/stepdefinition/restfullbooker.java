package com.restfullbooker.stepdefinition;

import com.restfullbooker.pojo.CreatBooking;
import com.restfullbooker.config.Endpoint;
import com.restfullbooker.utils.TestDataBuild;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.IOException;

import static com.restfullbooker.utils.utils.baseRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class restfullbooker {

    private RequestSpecification request;
    private Response response;
    private TestDataBuild data = new TestDataBuild();
    private static String bookingId;
    private CreatBooking bookingPayload;

    @Given("user creates a booking with the following details {string} {string} {int} {boolean} {string} {string} {string}")
    public void user_creates_a_booking_with_the_following_details(
            String firstname, String lastname, int totalprice, boolean depositpaid,
            String checkin, String checkout, String additionalneeds) throws IOException {
        
        // Store the booking payload for later validation if needed
        bookingPayload = data.creatBookingPayload(firstname, lastname, totalprice,
                depositpaid, checkin, checkout, additionalneeds);
                
        request = given()
                .spec(baseRequest())
                .body(bookingPayload);
    }

    @When("User calls {string} with {string} request")
    public void user_calls_with_request(String resource, String method) {
        try {
            Endpoint endpoint = Endpoint.valueOf(resource.replaceAll(" ", ""));
            ResponseSpecification responseSpec = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .build();

            // For this example, we'll only handle POST for CreateBooking
            if (method.equalsIgnoreCase("POST")) {
                response = request.when().post(endpoint.getResource());
                // Store the booking ID from the response
                if (response.getStatusCode() == 200) {
                    bookingId = response.jsonPath().getString("bookingid");
                }
            } else {
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }

            // Log the response
            System.out.println("\n========== " + method + " Request ==========");
            System.out.println("URL: " + endpoint.getResource());
            System.out.println("\n========== Response ==========");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.asPrettyString());
            System.out.println("==============================\n");

        } catch (Exception e) {
            System.err.println("Error making API call: " + e.getMessage());
            throw e;
        }
    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }






}


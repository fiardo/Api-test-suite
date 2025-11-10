package com.restfullbooker.stepdefinition;

import com.addplace.practices.Auth;
import com.restfullbooker.tests.resources.Endpoint;
import com.restfullbooker.tests.resources.TestDataBuild;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;

import static com.restfullbooker.utils.utils.baseRequest;
import static com.restfullbooker.utils.utils.getGlobalValue;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class restfullbooker {

    RequestSpecification response;
    Response requestsent;
    ResponseSpecification responsespces;
    TestDataBuild data = new TestDataBuild();
    String bookingId;
    String resource;
    String Token;

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
    public void user_calls_with_post_request(String resource, String method) {
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
    public void verify_status_code(Integer expectedStatusCode) {
        System.out.println("Expected Status Code: " + expectedStatusCode);
        System.out.println("Actual Status Code: " + requestsent.getStatusCode());
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

    @Then("user validates the create booking response matches the JSON schema")
    public void validate_schema() throws IOException {
        // Load the JSON schema file (keep it under src/test/resources)
        File schemaFile = new File("D:\\Api-test-suite\\src\\test\\java\\com\\restfullbooker\\tests\\resources\\CreateBookingschema.json");

        // Validate the response against the schema
        requestsent.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));

        System.out.println("✅ Schema validation passed for Create Booking API response");
    }

    @Given("{string} with {string} request with {string} {string}")
    public void Auth(String key, String method , String username , String password) throws IOException {

        Endpoint endpoint = Endpoint.valueOf(key);
        response = given().spec(baseRequest()).body(data.authPayload(getGlobalValue("username"), getGlobalValue("password")));
                //.body(data.authPayload(username, password));

        responsespces = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();



        if (method.equalsIgnoreCase("POST"))
            requestsent = response.when().post(endpoint.getResource());
        else if (method.equalsIgnoreCase("GET"))
            requestsent = response.when().get(endpoint.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            requestsent = response.when().delete(endpoint.getResource());

        //System.out.println("========== Response ==========");
        //System.out.println(requestsent.asPrettyString());
       // System.out.println("===================================");

        requestsent.then().spec(responsespces).extract().response();

        String responseString = requestsent.asString();
        JsonPath js = new JsonPath(responseString);
        Token = js.get("token").toString();
        System.out.println("Token: " + Token);


    }

    @When("{string} with {string} request")
    public void with_and_request(String key, String method) throws IOException {
        Endpoint endpoint = Endpoint.valueOf(key);
        resource = endpoint.getResource();

        // For DELETE request, append the booking ID to the resource URL
        if (method.equalsIgnoreCase("DELETE") && bookingId != null) {
            resource = resource + bookingId;
            System.out.println("DELETE URL: " + resource);

            requestsent = given()
                    .spec(baseRequest())
                    .when()
                    .delete(resource);

            System.out.println("Status Code: " + requestsent.getStatusCode());
            System.out.println("Response: " + requestsent.asString());
        }
    }

    // Status code verification is handled by verify_status_code method



}
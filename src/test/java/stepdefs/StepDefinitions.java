package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoBddFramework.AddPlace;
import pojoBddFramework.Location;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class StepDefinitions extends Utils {

    ResponseSpecification responsespces;
    RequestSpecification response;
    Response requestsent;
    TestDataBuild data = new TestDataBuild();


    @Given("Add Place Payload")
    public void add_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        response = given().spec(baseResponse()).body(data.addPlacePayload());
    }

    @When("User calls {string} with POST request")
    public void user_calls_with_post_request(String string) {
        // Write code here that turns the phrase above into concrete actions
        responsespces = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        requestsent = response.when().post("/maps/api/place/add/json").then().spec(responsespces).extract().response();
    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
       assertEquals(requestsent.getStatusCode(),200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        // Write code here that turns the phrase above into concrete actions
        String getresponse = requestsent.asString();
        JsonPath js = new JsonPath(getresponse);
        assertEquals(js.get(key).toString(), value);


    }
}

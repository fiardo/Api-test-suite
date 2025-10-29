package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResource;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public class place extends Utils {

    ResponseSpecification responsespces;
    RequestSpecification response;
    Response requestsent;
    TestDataBuild data = new TestDataBuild();
    static String  place_id;


    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String phone_number, String address) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        response = given().spec(baseResponse()).body(data.addPlacePayload(name, phone_number, address));
    }

    @When("User calls {string} with {string} request")
    public void user_calls_with_post_request(String resource , String method) {
        // Write code here that turns the phrase above into concrete actions
        APIResource apiresource = APIResource.valueOf(resource);
        responsespces = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST"))
            requestsent = response.when().post(apiresource.getResource());
        else if (method.equalsIgnoreCase("GET"))
            requestsent = response.when().get(apiresource.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            requestsent = response.when().delete(apiresource.getResource());


        System.out.println("========== Response Body ==========");
        System.out.println(requestsent.asPrettyString());
        System.out.println("===================================");

        requestsent.then().spec(responsespces).extract().response();


        //requestsent = response.when().post(apiresource.getResource()).then().spec(responsespces).extract().response();
    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode, (Integer) requestsent.getStatusCode());
    }

    @And("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        // Write code here that turns the phrase above into concrete actions
        String getresponse = requestsent.asString();
        JsonPath js = new JsonPath(getresponse);
        assertEquals(js.get(key).toString(), value);
    }

    @And("Store the {string} from response body")
    public void store_value_from_response(String key) {
        String responseString = requestsent.asString();
        JsonPath js = new JsonPath(responseString);
        place_id = js.get(key).toString();
        System.out.println("Value from response: " + place_id);
        // Store the value in a class variable or test context for later use
        // For example: testContext.setContext(key, js.get(key));
    }

    @Given("Get Place Payload")
    public void get_place_payload() throws IOException {
        response = given().spec(baseResponse()).queryParam("place_id", place_id);



    }

    @Given("Delete Place Payload")
    public void delete_place_payload() throws IOException {
        response = given().spec(baseResponse()).body("{\"place_id\":\"" + place_id + "\"}");

    }

}

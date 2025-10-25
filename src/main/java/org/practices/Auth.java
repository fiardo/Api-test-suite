package org.practices;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

//Demonstrates retrieving an OAuth access token and using it to call a protected API endpoint using RestAssured.

public class Auth {

    public static void main (String[] args) {

        // Build and send the token request using form parameters.
        // This POST returns a JSON body that includes an access_token.
        String response = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when()
                .log() // log request for debugging
                .all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        // Print raw JSON response for inspection
        System.out.println(response);

        // Parse the JSON response to extract the access_token field
        JsonPath js = new JsonPath(response); // parse the JSON string
        String accessToken = js.getString("access_token"); // extract token

        // Use the extracted access token as a query parameter to call a protected endpoint.
        // The token is passed as "access_token" query parameter (per this API's contract).
        String courseDetails = given().queryParam("access_token", accessToken)
                .when()
                .log() // log the outgoing request (including the access token in query)
                .all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();

        // Print the protected resource response
        System.out.println(courseDetails);
    }

}
Feature: Add Place API

  Scenario: Verify that user can successfully add a place using AddPlaceApi
    Given Add Place Payload
    When User calls "AddPlaceAPI" with POST request
    Then API call is successful with status code 200
    And "scope" in response body is "APP"
    And "status" in response body is "OK"






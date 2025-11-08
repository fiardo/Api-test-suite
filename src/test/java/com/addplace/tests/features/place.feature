Feature: Add Place API

  Scenario Outline: Verify that user can successfully add , get , delete place using AddPlaceApi
    Given Add Place Payload with "<name>" "<phone_number>" "<address>"
    When User calls "AddPlaceAPI" with "POST" request
    Then API call is successful with status code 200
    And "scope" in response body is "APP"
    And "status" in response body is "OK"
    And Store the "place_id" from response body

    Given Get Place Payload
    When User calls "getPlaceAPI" with "GET" request
    Then API call is successful with status code 200
    And "address" in response body is "<address>"

    Given Delete Place Payload
    When User calls "deletePlaceAPI" with "DELETE" request
    Then API call is successful with status code 200
    And "status" in response body is "OK"

    Examples:
      | name         | phone_number       | address                           |
      | Pravin house | (+91) 900 000 3937 | 1143, jai prakash nagar, adhartal |
      | Alok sharma  | (+91) 900 000 0000 | 1143, way prakash nagar, Jamnagar |



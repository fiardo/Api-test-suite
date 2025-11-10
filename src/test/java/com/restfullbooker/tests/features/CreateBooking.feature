@bookerAPI @createBooking
Feature: To create and delete bookings in restful-booker

  @create
  Scenario Outline: Create a new booking in restful-booker
    Given user creates a booking with the following details "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    When User calls "CreateBooking" with "POST" request
    Then API call is successful with status code 200
    And store "bookingid" from response body
    And user validates the create booking response matches the JSON schema

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Pravin    | Garg     | 100        | true        | 2022-01-01 | 2022-01-02 | Breakfast       |

  @delete
  Scenario: Generate Auth Token and Delete the Booking
    Given "Auth" with "POST" request with "username" "password"
    When "DeleteBooking" with "DELETE" request
    Then API call is successful with status code 200


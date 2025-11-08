@bookerAPI @createBooking
Feature: To create a new booking in restful-booker

  Scenario Outline: Create a new booking in restful-booker
    Given user creates a booking with the following details "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    When User calls "CreateBooking" with "POST" request
    Then API call is successful with status code 200
    And store "bookingid" from response body

Examples:
  | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
  | Pravin    | Garg     | 100        | true        | 2022-01-01 | 2022-01-02 | Breakfast       |



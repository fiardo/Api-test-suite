package com.restfullbooker.tests.resources;

import com.restfullbooker.pojo.Auth;
import com.restfullbooker.pojo.CreatBooking;
import com.restfullbooker.pojo.BookingDates;

public class TestDataBuild {


    public CreatBooking creatBookingPayload(String firstname,
                                            String lastname,
                                            int totalprice,
                                            boolean depositpaid,
                                            String checkin,
                                            String checkout,
                                            String additionalneeds)
    {


        CreatBooking creatbooking = new CreatBooking();
        creatbooking.setFirstname(firstname);
        creatbooking.setLastname(lastname);
        creatbooking.setTotalprice(Integer.parseInt(String.valueOf(totalprice)));
        creatbooking.setDepositpaid(Boolean.parseBoolean(String.valueOf(depositpaid)));
        creatbooking.setAdditionalneeds(additionalneeds);


        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        creatbooking.setBookingdates(bookingdates);
        return creatbooking;
    }


    public Auth authPayload(String username, String password) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(password);
        return auth;
    }

}

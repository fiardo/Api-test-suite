package com.restfullbooker.utils;

import com.restfullbooker.pojo.BookingDates;
import com.restfullbooker.pojo.CreatBooking;

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


}

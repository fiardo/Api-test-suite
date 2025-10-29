package resources;

import pojoBddFramework.AddPlace;
import pojoBddFramework.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {



    public AddPlace addPlacePayload(String name , String phone_number , String address)  {

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress(address);
        p.setPhone_number(phone_number);
        p.setName(name);
        p.setLanguage("French-IN");
        p.setWebsite("http://google.com");

        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);
        return p;


    }
}

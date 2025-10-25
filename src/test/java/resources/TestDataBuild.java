package resources;

import pojoBddFramework.AddPlace;
import pojoBddFramework.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayload() {

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setPhone_number("(+91) 983 893 3937");
        p.setName("Frontline house");
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

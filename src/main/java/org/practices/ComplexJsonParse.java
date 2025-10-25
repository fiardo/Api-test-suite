package org.practices;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import resource.Payload;

public class ComplexJsonParse {

    public static void main(String[] args){


         //Print No of courses returned by API
        JsonPath js = new JsonPath(Payload.CoursePrice());
        int count = js.getInt("courses.size()");
        System.out.println("Course Count : " + count);
         //.Print Purchase Amount
        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount  : " + amount);
        //Print Title of the first course
        String title = js.getString("courses[0].title");
        System.out.println("the first course : " + title);
        //Print All course titles and their respective Prices
        for (int i = 0; i < count; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            System.out.println(courseTitle + " : " + price);
        }

       //Print no of copies sold by RPA Course
        for (int i = 0; i < count; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println("\nCopies sold by RPA course: " + copies);
                break;
            }
        }
        //Verify if Sum of all Course prices matches with Purchase Amount
        int CoursePrice = 0;
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int Copies = js.getInt("courses[" + i + "].copies");
            CoursePrice += price * Copies; //CoursePrice = CoursePrice + (price * copies);
        }
        System.out.println("Total Course Price will be : " + CoursePrice);
        Assert.assertEquals(amount,CoursePrice);
        if (CoursePrice == amount)
            System.out.println("Sum of all course prices matches with Purchase Amount which is " + CoursePrice);
        else
            System.out.println(" Sum of all course prices does not matches with Purchase Amount");


    }

}

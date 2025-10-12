package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import resource.Payload;
import resource.ReUsableMethods;
import static io.restassured.RestAssured.given;
import org.testng.annotations.DataProvider;

public class DynamicJson {

    private String id; // Class-level variable

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn , String aisle)
    {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String resp = given().log().all().
                header("Content-Type","application/json").
                body(Payload.Addbook(isbn , aisle)).
                when().
                post("/Library/Addbook.php").
                then().
                assertThat().
                statusCode(200).extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(resp);
        id = js.getString("ID");
        String Msg = js.getString("Msg");

        System.out.println("ID :" + id);
        System.out.println("Msg :" + Msg);
        Assert.assertEquals(Msg, "successfully added");

        // Delete book
        String delresp = given().log().all().
                header("Content-Type","application/json").
                body("{\n" + "    \"ID\": \"" + id + "\"\n" + "}").
                when().
                post("/Library/DeleteBook.php").
                then().
                assertThat().
                statusCode(200).extract().response().asString();
        JsonPath js1 = ReUsableMethods.rawToJson(delresp);
        String deleteMsg = js1.getString("msg");
        System.out.println("Msg :" + deleteMsg);
        //Assert.assertEquals(Msg, "book is successfully deleted");
    }

    @DataProvider(name="BooksData")
    public Object[][] getdata()
    {
       return new Object[][] {{"qwer","1234"},{"asdf","1234"},{"zxcv","1234"}};

    }

}

package com.addplace.tests.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification baseresponse;


    public static RequestSpecification baseResponse() throws IOException {

        if (baseresponse == null) {
        PrintStream log = new PrintStream(new FileOutputStream("log.txt"));

        baseresponse = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                .addQueryParam("key" , "qaclick123")
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .setContentType(ContentType.JSON).build();
        return baseresponse;
        }
        return baseresponse;

    }


    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("D:\\Api-test-suite\\src\\test\\java\\resources\\global.properties");
        prop.load(file);
        return prop.getProperty(key);


    }


}

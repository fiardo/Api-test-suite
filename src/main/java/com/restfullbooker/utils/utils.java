package com.restfullbooker.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class utils {

    private static RequestSpecification baserequest;

    public static RequestSpecification baseRequest() throws IOException {
        if (baserequest == null) {
            baserequest = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                .setContentType(ContentType.JSON).build();
        }
        return baserequest;

    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("D:\\Api-test-suite\\src\\main\\java\\com\\restfullbooker\\cofig\\config.properties");
        prop.load(file);
        return prop.getProperty(key);
    }
}

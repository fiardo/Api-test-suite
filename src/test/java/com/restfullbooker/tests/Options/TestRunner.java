package com.restfullbooker.tests.Options;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/restfullbooker/tests/features", glue = {"com/restfullbooker/stepdefinition"} )

public class TestRunner {
}

package com.addplace.tests.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features", glue = {"com/addplace/tests/stepdefs"} )

public class TestRunner {
}





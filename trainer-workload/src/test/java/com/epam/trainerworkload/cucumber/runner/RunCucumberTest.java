package com.epam.trainerworkload.cucumber.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"com.epam.trainerworkload.cucumber.steps", "com.epam.trainerworkload.cucumber.config"},
        plugin = {"pretty"}
)
public class RunCucumberTest {
}


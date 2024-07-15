package com.epam.crmgym.cucumber.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"com.epam.crmgym.cucumber.steps", "com.epam.crmgym.cucumber.config"},
        plugin = {"pretty"}
)
@ActiveProfiles("test")
public class RunCucumberTest {
}



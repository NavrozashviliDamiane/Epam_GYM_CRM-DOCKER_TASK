package com.epam.crmgym.cucumber.config;



import com.epam.crmgym.CrmgymApplication;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = CrmgymApplication.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {




}

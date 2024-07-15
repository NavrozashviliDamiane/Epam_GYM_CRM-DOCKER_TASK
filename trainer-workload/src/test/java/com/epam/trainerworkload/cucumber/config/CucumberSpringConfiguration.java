package com.epam.trainerworkload.cucumber.config;

import com.epam.trainerworkload.TrainerWorkloadApplication;
import com.epam.trainerworkload.service.TrainerSummaryService;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = TrainerWorkloadApplication.class)
public class CucumberSpringConfiguration {
}

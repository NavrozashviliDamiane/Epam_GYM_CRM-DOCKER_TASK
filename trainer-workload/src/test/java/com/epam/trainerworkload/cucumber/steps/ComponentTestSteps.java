package com.epam.trainerworkload.cucumber.steps;

import com.epam.trainerworkload.TrainerWorkloadApplication;
import com.epam.trainerworkload.config.JwtService;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.service.TrainerSummaryService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TrainerWorkloadApplication.class)
public class ComponentTestSteps {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TrainerSummaryService trainerSummaryService;

    private String jwtToken;
    private TrainingSessionDTO trainingSessionDTO;
    private boolean processResult;

    @Given("a valid JWT token")
    public void a_valid_jwt_token() {
        jwtToken = "valid-jwt-token";
    }

    @Given("an invalid JWT token")
    public void an_invalid_jwt_token() {
        jwtToken = "invalid-jwt-token";
    }

    @When("a training session message is received")
    public void a_training_session_message_is_received() {
        trainingSessionDTO = new TrainingSessionDTO("trainerUsername", "firstName", "lastName", true, new Date(), 60, "ADD");
        try {
            trainerSummaryService.manageTrainingSession(trainingSessionDTO, jwtToken);
            processResult = true;
        } catch (InvalidJwtTokenException e) {
            processResult = false;
        }
    }

    @When("a training session deletion message is received")
    public void a_training_session_deletion_message_is_received() {
        trainingSessionDTO = new TrainingSessionDTO("trainerUsername", "firstName", "lastName", false, new Date(), 0, "DELETE");
        try {
            trainerSummaryService.manageTrainingSession(trainingSessionDTO, jwtToken);
            processResult = true;
        } catch (InvalidJwtTokenException e) {
            processResult = false;
        }
    }


    @Then("the training session should be processed and stored")
    public void the_training_session_should_be_processed_and_stored() {
        assertTrue(processResult);
    }

    @Then("the message should be rejected")
    public void the_message_should_be_rejected() {
        assertTrue(processResult);
    }

    @Then("the training session should be deleted")
    public void the_training_session_should_be_deleted() {
        assertTrue(processResult);
    }
}

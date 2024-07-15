package com.epam.trainerworkload.cucumber.steps;

import com.epam.trainerworkload.TrainerWorkloadApplication;
import com.epam.trainerworkload.actions.ActionType;
import com.epam.trainerworkload.actions.TrainingAction;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;
import com.epam.trainerworkload.service.impl.TrainerSummaryServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TrainerWorkloadApplication.class)
public class TrainerManagementSteps {

    private final TrainerSummaryRepository repository = Mockito.mock(TrainerSummaryRepository.class);
    private final TrainerSummaryServiceImpl trainerSummaryService = new TrainerSummaryServiceImpl(repository);

    private String transactionId = "12345";
    private TrainingSessionDTO trainingSessionDTO;
    private List<TrainerSummary> trainerSummaries = new ArrayList<>();

    @Given("the system has no trainer with username {string}")
    public void ensureNoTrainer(String trainerUsername) {
    }

    @When("I create a trainer with first name {string}, last name {string}, username {string}, active status {string}, training date {string}, training duration {string}, and action type {string}")
    public void createTrainer(String firstName, String lastName, String trainerUsername, String isActive, String trainingDate, String trainingDuration, String actionType) {
        boolean active = Boolean.parseBoolean(isActive);
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(trainingDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + trainingDate);
        }
        ActionType type = ActionType.fromString(actionType);
        int duration = Integer.parseInt(trainingDuration);
        trainingSessionDTO = new TrainingSessionDTO(trainerUsername, firstName, lastName, active, date, duration, type.getAction());
        TrainingAction action = type.getTrainingAction();
        action.execute(trainingSessionDTO, transactionId, repository);
    }





    @Then("I should get the trainer with first name {string}, last name {string}, and username {string}")
    public void verifyRetrievedTrainer(String firstName, String lastName, String trainerUsername) {
        boolean trainerFound = false;
        for (TrainerSummary trainerSummary : trainerSummaries) {
            if (trainerSummary.getTrainerUsername().equals(trainerUsername) &&
                    trainerSummary.getFirstName().equals(firstName) &&
                    trainerSummary.getLastName().equals(lastName)) {
                trainerFound = true;
                break;
            }
        }
        System.out.println("Trainer Summaries:");
        for (TrainerSummary summary : trainerSummaries) {
            System.out.println(summary);
        }
        assertTrue(trainerFound);
    }

    @Given("the system has a trainer with username {string}")
    public void the_system_has_a_trainer_with_username(String trainerUsername) {
        Optional<TrainerSummary> optionalSummary = repository.findByTrainerUsername(trainerUsername);
        if (optionalSummary.isEmpty()) {
            TrainerSummary trainer = new TrainerSummary();
            trainer.setTrainerUsername(trainerUsername);
            repository.save(trainer);
        }
    }

    @When("I delete the trainer with username {string}")
    public void deleteTrainer(String trainerUsername) {
        ActionType type = ActionType.DELETE;
        trainingSessionDTO = new TrainingSessionDTO(trainerUsername, "", "", false, new Date(), 0, type.getAction());
        TrainingAction action = type.getTrainingAction();
        action.execute(trainingSessionDTO, transactionId, repository);
    }

    @Then("the trainer should be deleted successfully")
    public void verifyTrainerDeletion() {
        Optional<TrainerSummary> optionalSummary = repository.findByTrainerUsername(trainingSessionDTO.getTrainerUsername());
        assertFalse(optionalSummary.isPresent());
    }


    @When("I create a trainer with invalid details")
    public void createTrainerWithInvalidDetails() {
        trainingSessionDTO = new TrainingSessionDTO("", "", "", false, new Date(), 0, "");
    }

    @Then("the trainer creation should fail")
    public void verifyTrainerCreationFails() {
        boolean exceptionThrown = false;
        try {
            ActionType type = ActionType.fromString(trainingSessionDTO.getActionType());
            TrainingAction action = type.getTrainingAction();
            action.execute(trainingSessionDTO, transactionId, repository);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}

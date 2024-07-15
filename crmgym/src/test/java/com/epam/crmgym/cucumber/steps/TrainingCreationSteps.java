package com.epam.crmgym.cucumber.steps;

import com.epam.crmgym.controller.TrainingController;
import com.epam.crmgym.dto.training.TrainingRequest;
import com.epam.crmgym.service.TrainingService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TrainingCreationSteps {

    @Autowired
    private TrainingController trainingController;

    @MockBean
    private TrainingService trainingService;
    private List<TrainingRequest> trainingRequests;



    private TrainingRequest trainingRequest;
    private ResponseEntity<String> response;



    @Given("a training request with the following details:")
    public void aTrainingRequestWithDetails(DataTable dataTable) {
        trainingRequests = new ArrayList<>();
        List<Map<String, String>> details = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : details) {
            String username = row.get("Username");
            String trainerUsername = row.get("TrainerUsername");
            String trainingName = row.get("TrainingName");
            String trainingDateString = row.get("TrainingDate");
            Integer trainingDuration = null;
            if (row.get("TrainingDuration")!= null) {
                trainingDuration = Integer.parseInt(row.get("TrainingDuration"));
            }

            if (trainingDateString!= null) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date trainingDate = sdf.parse(trainingDateString);
                    TrainingRequest trainingRequest = new TrainingRequest(username, trainerUsername, trainingName, trainingDate, trainingDuration);
                    trainingRequests.add(trainingRequest);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @When("I add the training")
    public void iAddTheTraining() {
        for (TrainingRequest trainingRequest : trainingRequests) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date trainingDate = trainingRequest.getTrainingDate();

            Mockito.doNothing().when(trainingService).createTraining(
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.anyString(),
                    Mockito.eq(trainingDate),
                    Mockito.anyInt()
            );

            response = trainingController.addTraining(trainingRequest);
        }
    }

    @Then("the training should be added successfully")
    public void theTrainingShouldBeAddedSuccessfully() {
        for (TrainingRequest trainingRequest : trainingRequests) {
            Mockito.verify(trainingService).createTraining(
                    trainingRequest.getUsername(),
                    trainingRequest.getTrainerUsername(),
                    trainingRequest.getTrainingName(),
                    trainingRequest.getTrainingDate(),
                    trainingRequest.getTrainingDuration()
            );

            Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Then("the training should not be added and an error response should be returned")
    public void theTrainingShouldNotBeAddedAndAnErrorResponseShouldBeReturned() {
        Mockito.verify(trainingService, Mockito.never()).createTraining(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(Date.class),
                Mockito.anyInt()
        );

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

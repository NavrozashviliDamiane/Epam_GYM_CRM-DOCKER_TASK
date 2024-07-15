package com.epam.trainerworkload.unit.dto;

import com.epam.trainerworkload.dto.TrainingSessionDTO;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainingSessionDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();



    @Test
    public void testInvalidTrainingSessionDTO() {
        TrainingSessionDTO dto = new TrainingSessionDTO(null, "", "Doe", true,
                new Date(), 0, "INVALID");

        assertEquals(6, validator.validate(dto).size());
    }

    @Test
    public void testBlankTrainerUsername() {
        TrainingSessionDTO dto = new TrainingSessionDTO("", "John", "Doe", true,
                new Date(), 60, "ADD");

        assertEquals(1, validator.validateProperty(dto, "trainerUsername").size());
    }

    @Test
    public void testBlankFirstName() {
        TrainingSessionDTO dto = new TrainingSessionDTO("john_doe", "", "Doe", true,
                new Date(), 60, "ADD");

        assertEquals(1, validator.validateProperty(dto, "firstName").size());
    }

    @Test
    public void testBlankLastName() {
        TrainingSessionDTO dto = new TrainingSessionDTO("john_doe", "John", "", true,
                new Date(), 60, "ADD");

        assertEquals(1, validator.validateProperty(dto, "lastName").size());
    }

    @Test
    public void testInvalidTrainingDuration() {
        TrainingSessionDTO dto = new TrainingSessionDTO("john_doe", "John", "Doe", true,
                new Date(), 0, "ADD");

        assertEquals(1, validator.validateProperty(dto, "trainingDuration").size());
    }

    @Test
    public void testInvalidActionType() {
        TrainingSessionDTO dto = new TrainingSessionDTO("john_doe", "John", "Doe", true,
                new Date(), 60, "INVALID");

        assertEquals(1, validator.validateProperty(dto, "actionType").size());
    }

    @Test
    public void testInvalidTrainingDate() {
        // Using a past date which should fail the @FutureOrPresent constraint
        TrainingSessionDTO dto = new TrainingSessionDTO("john_doe", "John", "Doe", true,
                new Date(1000000000), 60, "ADD");

        assertEquals(1, validator.validateProperty(dto, "trainingDate").size());
    }
}

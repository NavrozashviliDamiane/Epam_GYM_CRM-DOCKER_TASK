//package com.epam.trainerworkload.unit.entity;
//
//
//import com.epam.trainerworkload.entity.TrainerSummary;
//import jakarta.validation.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TrainerSummaryTest {
//
//    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    private final Validator validator = factory.getValidator();
//
//    @Test
//    public void testValidTrainerSummary() {
//        TrainerSummary summary = new TrainerSummary("1", "john_doe", "John", "Doe", true, null);
//
//        assertTrue(validator.validate(summary).isEmpty());
//    }
//
//    @Test
//    public void testBlankTrainerUsername() {
//        TrainerSummary summary = new TrainerSummary("1", "", "John", "Doe", true, null);
//
//        assertEquals(1, validator.validateProperty(summary, "trainerUsername").size());
//    }
//
//    @Test
//    public void testBlankFirstName() {
//        TrainerSummary summary = new TrainerSummary("1", "john_doe", "", "Doe", true, null);
//
//        assertEquals(1, validator.validateProperty(summary, "firstName").size());
//    }
//
//    @Test
//    public void testBlankLastName() {
//        TrainerSummary summary = new TrainerSummary("1", "john_doe", "John", "", true, null);
//
//        assertEquals(1, validator.validateProperty(summary, "lastName").size());
//    }
//
//    @Test
//    public void testNullYearlySummary() {
//        TrainerSummary summary = new TrainerSummary("1", "john_doe", "John", "Doe", true, null);
//
//        assertTrue(validator.validateProperty(summary, "yearlySummary").isEmpty());
//    }
//
//    @Test
//    public void testEmptyYearlySummary() {
//        TrainerSummary summary = new TrainerSummary("1", "john_doe", "John", "Doe", true, Map.of());
//
//        assertTrue(validator.validateProperty(summary, "yearlySummary").isEmpty());
//    }
//
//}

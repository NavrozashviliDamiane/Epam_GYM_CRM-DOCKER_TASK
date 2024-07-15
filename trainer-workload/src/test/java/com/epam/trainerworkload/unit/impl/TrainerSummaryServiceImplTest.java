package com.epam.trainerworkload.unit.impl;


import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;
import com.epam.trainerworkload.service.impl.TrainerSummaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerSummaryServiceImplTest {

    @Mock
    private TrainerSummaryRepository repository;

    @InjectMocks
    private TrainerSummaryServiceImpl service;

    private TrainingSessionDTO trainingSessionDTO;

    @BeforeEach
    void setUp() {
        trainingSessionDTO = new TrainingSessionDTO();
        trainingSessionDTO.setActionType("ADD");
        trainingSessionDTO.setTrainerUsername("john_doe");
        trainingSessionDTO.setFirstName("John");
        trainingSessionDTO.setLastName("Doe");
        trainingSessionDTO.setIsActive(true);
        trainingSessionDTO.setTrainingDate(new Date());
        trainingSessionDTO.setTrainingDuration(60);
    }

    @Test
    void testManageTrainingSession_AddNewSummary() {
        when(repository.findByTrainerUsername(trainingSessionDTO.getTrainerUsername())).thenReturn(Optional.empty());

        service.manageTrainingSession(trainingSessionDTO, "123");

        verify(repository).save(any(TrainerSummary.class));
        verify(repository, never()).delete(any(TrainerSummary.class));
    }

    @Test
    void testManageTrainingSession_UpdateExistingSummary() {
        TrainerSummary existingSummary = new TrainerSummary();
        existingSummary.setTrainerUsername("john_doe");
        existingSummary.setFirstName("John");
        existingSummary.setLastName("Doe");
        existingSummary.setIsActive(true);
        existingSummary.setYearlySummary(new HashMap<>());

        when(repository.findByTrainerUsername(trainingSessionDTO.getTrainerUsername())).thenReturn(Optional.of(existingSummary));

        service.manageTrainingSession(trainingSessionDTO, "123");

        verify(repository).save(existingSummary);
        verify(repository, never()).delete(any(TrainerSummary.class));
    }

    @Test
    void testManageTrainingSession_DeleteSummary() {
        trainingSessionDTO.setActionType("DELETE");

        TrainerSummary existingSummary = new TrainerSummary();
        when(repository.findByTrainerUsername(trainingSessionDTO.getTrainerUsername())).thenReturn(Optional.of(existingSummary));

        service.manageTrainingSession(trainingSessionDTO, "123");

        verify(repository).delete(existingSummary);
        verify(repository, never()).save(any(TrainerSummary.class));
    }

    @Test
    void testManageTrainingSession_InvalidActionType() {
        trainingSessionDTO.setActionType("INVALID");

        assertThrows(IllegalArgumentException.class, () -> service.manageTrainingSession(trainingSessionDTO, "123"));
    }
}

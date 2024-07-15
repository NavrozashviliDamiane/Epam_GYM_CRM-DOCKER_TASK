package com.epam.trainerworkload.service.impl;

import com.epam.trainerworkload.actions.ActionType;
import com.epam.trainerworkload.actions.TrainingAction;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;
import com.epam.trainerworkload.service.TrainerSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TrainerSummaryServiceImpl implements TrainerSummaryService {

    private final TrainerSummaryRepository repository;


    public TrainerSummaryServiceImpl(TrainerSummaryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void manageTrainingSession(TrainingSessionDTO dto, String transactionId) {
        ActionType actionType;
        try {
            actionType = ActionType.fromString(dto.getActionType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action type: " + dto.getActionType());
        }

        TrainingAction action = actionType.getTrainingAction();
        if (action != null) {
            action.execute(dto, transactionId, repository);
        } else {
            throw new IllegalArgumentException("Unsupported action type: " + dto.getActionType());
        }
    }




}

package com.epam.trainerworkload.service;

import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;

import java.util.List;

public interface TrainerSummaryService {
    void manageTrainingSession(TrainingSessionDTO dto, String transactionId);


}

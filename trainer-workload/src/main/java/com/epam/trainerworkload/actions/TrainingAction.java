package com.epam.trainerworkload.actions;

import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;

public interface TrainingAction {
    void execute(TrainingSessionDTO dto, String transactionId, TrainerSummaryRepository repository);
}

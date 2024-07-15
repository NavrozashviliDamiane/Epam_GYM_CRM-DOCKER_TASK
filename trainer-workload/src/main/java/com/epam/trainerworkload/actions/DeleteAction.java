package com.epam.trainerworkload.actions;

import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DeleteAction implements TrainingAction {
    @Override
    public void execute(TrainingSessionDTO dto, String transactionId, TrainerSummaryRepository repository) {
        Optional<TrainerSummary> optionalSummary = repository.findByTrainerUsername(dto.getTrainerUsername());
        if (optionalSummary.isPresent()) {
            repository.delete(optionalSummary.get());
            log.info("Transaction ID: {} - Deleted training summary for trainer: {}", transactionId, dto.getTrainerUsername());
        } else {
            log.info("Transaction ID: {} - No training summary found for trainer: {}", transactionId, dto.getTrainerUsername());
        }
    }
}

package com.epam.trainerworkload.actions;

import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainerSummary;
import com.epam.trainerworkload.repository.TrainerSummaryRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AddAction implements TrainingAction {
    @Override
    public void execute(TrainingSessionDTO dto, String transactionId, TrainerSummaryRepository repository) {
        Optional<TrainerSummary> optionalSummary = repository.findByTrainerUsername(dto.getTrainerUsername());
        TrainerSummary summary;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTrainingDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        if (optionalSummary.isEmpty()) {
            summary = new TrainerSummary();
            summary.setTrainerUsername(dto.getTrainerUsername());
            summary.setFirstName(dto.getFirstName());
            summary.setLastName(dto.getLastName());
            summary.setIsActive(dto.isActive());
            summary.setYearlySummary(new HashMap<>());

            Map<Integer, Integer> monthlySummary = new HashMap<>();
            monthlySummary.put(month, dto.getTrainingDuration());
            summary.getYearlySummary().put(year, monthlySummary);

            repository.save(summary);
            log.info("Transaction ID: {} - Created new training summary for trainer: {}", transactionId, dto.getTrainerUsername());
        } else {
            summary = optionalSummary.get();
            Map<Integer, Integer> monthlySummary = summary.getYearlySummary().getOrDefault(year, new HashMap<>());
            int newDuration = monthlySummary.getOrDefault(month, 0) + dto.getTrainingDuration();
            monthlySummary.put(month, newDuration);
            summary.getYearlySummary().put(year, monthlySummary);

            repository.save(summary);
            log.info("Transaction ID: {} - Updated training summary for trainer: {}", transactionId, dto.getTrainerUsername());
        }
    }
}

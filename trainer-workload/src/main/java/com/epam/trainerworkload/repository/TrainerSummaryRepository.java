package com.epam.trainerworkload.repository;

import com.epam.trainerworkload.entity.TrainerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TrainerSummaryRepository extends MongoRepository<TrainerSummary, String> {
    Optional<TrainerSummary> findByTrainerUsername(String trainerUsername);
}

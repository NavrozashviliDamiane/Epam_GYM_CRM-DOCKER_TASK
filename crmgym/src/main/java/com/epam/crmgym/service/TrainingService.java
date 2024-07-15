package com.epam.crmgym.service;

import com.epam.crmgym.entity.Training;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public interface TrainingService {



    @Transactional
    Training createTraining(String username, String trainerUsername,
                            String trainingName, Date trainingDate, Integer trainingDuration);


}

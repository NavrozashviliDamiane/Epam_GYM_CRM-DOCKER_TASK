package com.epam.trainerworkload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "trainer_summaries")
public class TrainerSummary {

    @Id
    private String id;

    @Indexed(unique = true)
    private String trainerUsername;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;
    private boolean isActive;
    private Map<Integer, Map<Integer, Integer>> yearlySummary;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}

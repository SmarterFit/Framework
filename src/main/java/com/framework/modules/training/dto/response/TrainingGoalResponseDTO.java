package com.framework.modules.training.dto.response;

import com.framework.common.enums.ExperienceLevel;
import com.framework.common.enums.Goal;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrainingGoalResponseDTO {
    UUID id;
    private Goal goal;
    private ExperienceLevel experienceLevel;
    private Integer weeklyFrequency;
}

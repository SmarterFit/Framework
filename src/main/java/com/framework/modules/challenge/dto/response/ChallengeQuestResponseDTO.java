package com.framework.modules.challenge.dto.response;

import com.framework.common.enums.ExperienceLevel;
import com.framework.framework.usermetric.entity.generic.MetricType;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeQuestResponseDTO {

    private UUID id;

    private MetricType metricType;

    private String title;

    private String challengeType;

    private ExperienceLevel experienceLevel;

    private List<DayOfWeek> daysOfWeek;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}

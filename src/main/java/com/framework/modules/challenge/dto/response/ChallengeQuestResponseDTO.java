package com.framework.modules.challenge.dto.response;

import com.framework.framework.usermetric.entity.generic.MetricType;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeQuestResponseDTO {

    private MetricType metricType;

    private String title;

    private List<DayOfWeek> daysOfWeek;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}

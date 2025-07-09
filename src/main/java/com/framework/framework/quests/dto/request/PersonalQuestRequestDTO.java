package com.framework.framework.quests.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalQuestRequestDTO {
    private String name;
    private String domain;
    private String description;
    private int weeklyFrequency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String metricType;
    private boolean completed;
}

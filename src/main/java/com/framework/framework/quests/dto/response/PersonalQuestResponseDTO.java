package com.framework.framework.quests.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalQuestResponseDTO {
    private UUID id;
    private String name;
    private String domain;
    private String description;
    private int weeklyFrequency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String metricType;
    private boolean completed;
}

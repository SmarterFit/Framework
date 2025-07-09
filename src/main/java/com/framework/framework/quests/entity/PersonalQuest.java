package com.framework.framework.quests.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalQuest {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String name;

    private String domain;

    private String description;

    private int weeklyFrequency;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String metricType;

    private boolean completed = false;
}
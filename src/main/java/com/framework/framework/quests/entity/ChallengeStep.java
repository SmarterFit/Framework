package com.framework.framework.quests.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeStep {

    @Id
    @GeneratedValue
    private UUID id;

    private String description;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private ChallengeDay day;
}
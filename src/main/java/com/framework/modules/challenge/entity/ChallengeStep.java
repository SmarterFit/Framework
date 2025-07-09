package com.framework.modules.challenge.entity;

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

    @Builder.Default
    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private ChallengeDay day;

}
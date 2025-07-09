package com.framework.framework.quests.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeTrail {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private PersonalQuest quest;

    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeDay> days = new ArrayList<>();
}
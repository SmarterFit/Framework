package com.framework.modules.challenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.useraccess.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "challenge_quest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ChallengeQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metric_type_id", nullable = false)
    private MetricType metricType;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "challenge_quest_week_days",
            joinColumns = @JoinColumn(name = "challenge_quest_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private List<DayOfWeek> daysOfWeek;


    @Column(name = "dt_created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}


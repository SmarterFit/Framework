package com.framework.framework.usermetric.entity.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metric_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class MetricType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String type;

    private String unit;

    @Builder.Default
    private boolean enabled = true;

    @Column(name = "min_threshold")
    private double minThreshold;

    @Column(name = "max_threshold")
    private double maxThreshold;


    @OneToMany(mappedBy = "metricType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AbstractMetricRecord> MetricRecord = new HashSet<AbstractMetricRecord>();

    @Column(name = "dt_created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {this.createdAt = LocalDateTime.now();}

}

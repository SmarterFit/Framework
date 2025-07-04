package com.framework.framework.usermetric.entity.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.modules.useraccess.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class AbstractMetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    private String source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metric_type_id", nullable = false)
    private MetricType metricType;


    @Column(name = "dt_created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public abstract double getValue();

    public abstract Map<String, Object> getDetails();
}

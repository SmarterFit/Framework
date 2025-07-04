package com.framework.modules.metric.repository;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMetricRepository extends JpaRepository<AbstractMetricRecord, UUID> {

    List<AbstractMetricRecord> findByProfileIdAndMetricTypeId(UUID profileId, UUID metricTypeId);

    List<AbstractMetricRecord> findByProfileIdAndMetricTypeIdOrderByCreatedAtAsc(UUID profileId, UUID metricTypeId);

    Optional<AbstractMetricRecord> findById(UUID id);
}

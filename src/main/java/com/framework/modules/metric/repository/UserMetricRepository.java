package com.framework.modules.metric.repository;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMetricRepository extends JpaRepository<AbstractMetricRecord, UUID> {

    List<AbstractMetricRecord> findByProfileIdAndMetricTypeId(UUID profileId, UUID metricTypeId);

    List<AbstractMetricRecord> findByProfileIdAndMetricTypeIdOrderByCreatedAtAsc(UUID profileId, UUID metricTypeId);

    @Query("SELECT r FROM AbstractMetricRecord r WHERE r.profile.id = :profileId AND r.createdAt = (" +
            "SELECT MAX(sub.createdAt) FROM AbstractMetricRecord sub " +
            "WHERE sub.profile.id = r.profile.id AND sub.metricType = r.metricType" +
            ")")
    List<AbstractMetricRecord> findLastsByProfileId(@Param("profileId") UUID profileId);

    Optional<AbstractMetricRecord> findById(UUID id);
}

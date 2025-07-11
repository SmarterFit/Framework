package com.framework.framework.usermetric.repository;

import com.framework.framework.usermetric.entity.grade.ClassGradeMetricRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassGradeMetricRecordRepository extends JpaRepository<ClassGradeMetricRecord, UUID> {
    List<ClassGradeMetricRecord> findByClassGroupId(UUID classGroupId);

    Optional<ClassGradeMetricRecord> findFirstByProfileIdAndMetricTypeIdAndClassGroupIdOrderByCreatedAtDesc
            (UUID profileId, UUID metricTypeId, UUID classGroupId);



}

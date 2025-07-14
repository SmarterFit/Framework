package com.framework.framework.usermetric.repository;

import com.framework.framework.usermetric.entity.educationcredit.EducationCreditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationCreditRecordRepository extends JpaRepository<EducationCreditRecord, UUID> {
    List<EducationCreditRecord> findByProfileId(UUID profileId);

    Optional<EducationCreditRecord> findFirstByProfileIdAndMetricTypeIdOrderByCreatedAtDesc(
         UUID profileId, UUID metricTypeId);
}

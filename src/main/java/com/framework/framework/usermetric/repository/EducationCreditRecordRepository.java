package com.framework.framework.usermetric.repository;

import com.framework.framework.usermetric.entity.educationcredit.EducationCreditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationCreditRecordRepository extends JpaRepository<EducationCreditRecord, UUID> {
        List<EducationCreditRecord> findByProfileId(UUID profileId);

        Optional<EducationCreditRecord> findFirstByProfileIdAndMetricTypeIdOrderByCreatedAtDesc(
                        UUID profileId, UUID metricTypeId);

        @Query("SELECT COALESCE(SUM(e.hours), 0) FROM EducationCreditRecord e WHERE e.profile.id = :profileId AND e.institution = :institution")
        double sumHoursByProfileIdAndInstitution(@Param("profileId") UUID profileId,
                        @Param("institution") String institution);

        @Query("""
                SELECT COALESCE(SUM(e.hours), 0)
                FROM EducationCreditRecord e
                WHERE e.profile.id = :profileId
                AND (
                        LOWER(e.institution) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                        LOWER(e.courseName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
        """)
        double sumHoursByProfileIdAndKeyword(
                        @Param("profileId") UUID profileId,
                        @Param("keyword") String keyword);
}

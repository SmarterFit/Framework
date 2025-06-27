package com.framework.modules.useraccess.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.framework.common.enums.ProfileMetricType;
import com.framework.modules.useraccess.entity.ProfileMetric;

public interface ProfileMetricRepository extends JpaRepository<ProfileMetric, UUID> {
      List<ProfileMetric> findByProfileIdOrderByCreatedAtDesc(UUID profileId);

      List<ProfileMetric> findByProfileIdAndTypeOrderByCreatedAtDesc(UUID profileId,
                  ProfileMetricType type);

      @Query(value = """
                  SELECT DISTINCT ON (type) *
                  FROM SF_PROFILE_METRIC
                  WHERE profile_id = :profileId
                  ORDER BY type, dt_created_at DESC
                  """, nativeQuery = true)
      List<ProfileMetric> findLastsProfileMetricsByProfileId(@Param("profileId") UUID profileId);

}

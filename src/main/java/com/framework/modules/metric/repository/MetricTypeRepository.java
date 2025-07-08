package com.framework.modules.metric.repository;

import com.framework.framework.usermetric.entity.generic.MetricType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MetricTypeRepository extends JpaRepository<MetricType, UUID> {

    Optional<MetricType> findByType(String type);

    Optional<MetricType> findByTypeAndEnabledTrue(String type);

    boolean existsByType(String type);
}

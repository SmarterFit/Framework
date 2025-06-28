package com.framework.framework.usermetric.metric.initializer;


import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.repository.MetricTypeRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Component
public class MetricTypeInitializer {
    private static final Logger logger = LoggerFactory.getLogger(MetricTypeInitializer.class);

    private final MetricTypeRepository metricTypeRepository;

    public MetricTypeInitializer(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    @Transactional
    @PostConstruct
    public void initializeMetricTypes() {
        try {
            createIfNotExists("GRADE", "points", 0, 10);
            createIfNotExists("WEIGHT", "kg", 0, 800);
            createIfNotExists("HEIGHT", "cm", 0, 300);
            logger.info("Metric types initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize metric types", e);
            throw new RuntimeException("Metric type initialization failed", e);
        }
    }

    private void createIfNotExists(String type, String unit, double minThreshold, double maxThreshold) {
        try {
            if (!metricTypeRepository.existsByType(type)) {
                MetricType metricType = new MetricType();
                metricType.setType(type);
                metricType.setUnit(unit);
                metricType.setMinThreshold(minThreshold);
                metricType.setMaxThreshold(maxThreshold);

                metricTypeRepository.save(metricType);
            }
        } catch (Exception e) {
            logger.error("Failed to create metric type: " + type, e);
            throw e;
        }
    }
}
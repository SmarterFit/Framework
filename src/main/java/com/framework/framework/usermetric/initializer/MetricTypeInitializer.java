package com.framework.framework.usermetric.initializer;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.handler.MetricHandler;
import com.framework.modules.metric.repository.MetricTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MetricTypeInitializer {
    private static final Logger logger = LoggerFactory.getLogger(MetricTypeInitializer.class);

    private final MetricTypeRepository metricTypeRepository;
    private final Set<String> systemMetricTypes = new HashSet<>();

    public MetricTypeInitializer(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    public Set<String> initializeMetricTypes() {
        try {
            createIfNotExists("Altura", "cm", 50, 300);
            createIfNotExists("Peso", "kg", 20, 800);
            createIfNotExists("Percentual de Gordura", "%", 1, 75);
            createIfNotExists("Massa Muscular", "kg", 10, 150);
            createIfNotExists("Cintura", "cm", 40, 200);
            createIfNotExists("Quadril", "cm", 40, 200);
            createIfNotExists("Braço", "cm", 15, 70);
            createIfNotExists("Coxa", "cm", 30, 120);
            createIfNotExists("Pressão Arterial", "sistólica/diastólica", 0, 0);
            createIfNotExists("Frequência Cardíaca", "bpm", 30, 220);
            logger.info("Metric types initialized successfully");
            return Set.copyOf(systemMetricTypes);

        } catch (Exception e) {
            logger.error("Failed to initialize metric types", e);
            throw new RuntimeException("Metric type initialization failed", e);
        }
    }

    public void createMetricType(MetricHandler metricHandler) {
        String type = metricHandler.getSupportedType();
        String unit = metricHandler.getUnit();
        double minThreshold = metricHandler.getMinThreshold();
        double maxThreshold = metricHandler.getMaxThreshold();

        if (!metricTypeRepository.existsByType(type)) {
            MetricType metricType = new MetricType();
            metricType.setType(type);
            metricType.setUnit(unit);
            metricType.setMinThreshold(minThreshold);
            metricType.setMaxThreshold(maxThreshold);
            metricTypeRepository.save(metricType);
        }
        systemMetricTypes.add(type);
    }

    private void createIfNotExists(String type, String unit, double minThreshold, double maxThreshold) {
        if (!metricTypeRepository.existsByType(type)) {
            MetricType metricType = new MetricType();
            metricType.setType(type);
            metricType.setUnit(unit);
            metricType.setMinThreshold(minThreshold);
            metricType.setMaxThreshold(maxThreshold);
            metricTypeRepository.save(metricType);
        }
        systemMetricTypes.add(type);
    }
}
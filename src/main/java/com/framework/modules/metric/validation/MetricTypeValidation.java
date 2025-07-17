package com.framework.modules.metric.validation;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.repository.MetricTypeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class MetricTypeValidation {

    private final MetricTypeRepository metricTypeRepository;

    public MetricTypeValidation(MetricTypeRepository metricTypeRepository) {
        this.metricTypeRepository = metricTypeRepository;
    }

    public MetricType validateMetricTypeById(UUID id) {
        return metricTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("MetricType not found with id: " + id));
    }

    public MetricType findMetricByType(String type) {
        return metricTypeRepository.findByType(type)
                .orElseThrow(() -> new BusinessException("MetricType not found with type: " + type));
    }

    public MetricType findMetricById(UUID id) {
        return metricTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("MetricType not found"));
    }

    public MetricType validateMetricTypeByName(String name) {
        return metricTypeRepository.findByType(name)
                .orElseThrow(() -> new BusinessException("MetricType not found with name: " + name));
    }

    public void validateNameUniqueness(String type) {
        Optional<MetricType> existing = metricTypeRepository.findByType(type);
        if (existing.isPresent()) {
            throw new BusinessException("MetricType with name '" + type + "' already exists.");
        }
    }

    public MetricType findEnabledMetricByType(String type) {
        return metricTypeRepository.findByTypeAndEnabledTrue(type)
                .orElseThrow(() -> new BusinessException("Enabled MetricType not found with type: " + type));
    }
}
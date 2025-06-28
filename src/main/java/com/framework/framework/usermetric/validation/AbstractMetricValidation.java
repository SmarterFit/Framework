package com.framework.framework.usermetric.validation;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.metric.repository.UserMetricRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AbstractMetricValidation {
    private final UserMetricRepository userMetricRepository;

    public AbstractMetricValidation(UserMetricRepository userMetricRepository) {
        this.userMetricRepository = userMetricRepository;
    }

    public AbstractMetricRecord findAbstractMetricRecord(UUID metricId) {
        return userMetricRepository.findById(metricId)
                .orElseThrow(() -> new RuntimeException("Metric not found"));
    }

}

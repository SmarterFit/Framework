package com.framework.framework.usermetric.validation.chain;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.modules.metric.dto.request.MetricDataDTO;

public interface MetricValidationStep {
    void validate(MetricValidationContext context);
}
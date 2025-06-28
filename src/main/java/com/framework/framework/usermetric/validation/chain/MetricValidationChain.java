package com.framework.framework.usermetric.validation.chain;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.modules.metric.dto.request.MetricDataDTO;

import java.util.List;

public class MetricValidationChain {

    private final List<MetricValidationStep> steps;

    public MetricValidationChain(List<MetricValidationStep> steps) {
        this.steps = steps;
    }

    public MetricValidationContext execute(MetricDataDTO request, MetricType metricType) {
        MetricValidationContext context = new MetricValidationContext(request, metricType);
        for (MetricValidationStep step : steps) {
            step.validate(context);
        }

        return context;
    }
}

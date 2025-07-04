package com.framework.framework.usermetric.validation.chain;

import com.framework.framework.usermetric.validation.MetricValidationContext;

public interface MetricValidationStep {
    void validate(MetricValidationContext context);
}
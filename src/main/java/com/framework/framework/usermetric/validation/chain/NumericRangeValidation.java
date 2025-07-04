package com.framework.framework.usermetric.validation.chain;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.validation.MetricValidationContext;

public class NumericRangeValidation implements MetricValidationStep {

    private final String fieldName;

    public NumericRangeValidation(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void validate(MetricValidationContext context) {
        try {
            double value = Double.parseDouble(context.getOriginalRequest().getData().get(fieldName).toString());

            if (value < context.getMetricType().getMinThreshold()) {
                throw new BusinessException(String.format("The value '%s' must be >= %.2f", fieldName,
                        context.getMetricType().getMinThreshold()));
            }

            if (value > context.getMetricType().getMaxThreshold()) {
                throw new BusinessException(String.format("The value '%s' must be <= %.2f", fieldName,
                        context.getMetricType().getMaxThreshold()));
            }

            context.putNormalized(fieldName, value);

        } catch (NumberFormatException e) {
            throw new BusinessException("The value '" + fieldName + "' must be numeric.");
        }
    }
}

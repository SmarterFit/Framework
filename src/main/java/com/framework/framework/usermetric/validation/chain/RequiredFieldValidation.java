package com.framework.framework.usermetric.validation.chain;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.validation.MetricValidationContext;

public class RequiredFieldValidation implements MetricValidationStep {

    private final String fieldName;

    public RequiredFieldValidation(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void validate(MetricValidationContext context) {
        if (!context.getOriginalRequest().getData().containsKey(fieldName)) {
            throw new BusinessException("The field '" + fieldName + "' is required.");
        }
    }

}

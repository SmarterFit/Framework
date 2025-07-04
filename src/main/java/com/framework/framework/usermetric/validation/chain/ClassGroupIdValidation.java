package com.framework.framework.usermetric.validation.chain;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.validation.ClassGroupValidation;

import java.util.UUID;

public class ClassGroupIdValidation implements MetricValidationStep {

    private final String fieldName;
    private final ClassGroupValidation validation;

    public ClassGroupIdValidation(String fieldName, ClassGroupValidation validation) {
        this.fieldName = fieldName;
        this.validation = validation;
    }

    @Override
    public void validate(MetricValidationContext context) {
        Object fieldValue = context.getOriginalRequest().getData().get(fieldName);

        if (fieldValue == null) {
            throw new BusinessException("Field '" + fieldName + "' is required.");
        }

        UUID id;
        try {
            id = UUID.fromString(fieldValue.toString());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Field '" + fieldName + "' must be a valid UUID.");
        }

        ClassGroup classGroup = validation.validateClassGroupById(id);
        context.putNormalized(fieldName, classGroup);
    }
}

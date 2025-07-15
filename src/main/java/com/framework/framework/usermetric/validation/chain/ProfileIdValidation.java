package com.framework.framework.usermetric.validation.chain;

import com.framework.common.exceptions.BusinessException;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.modules.useraccess.entity.Profile;
import com.framework.modules.useraccess.validation.ProfileValidation;

import java.util.UUID;

public class ProfileIdValidation implements MetricValidationStep {

    private final String fieldName;
    private final ProfileValidation validation;

    public ProfileIdValidation(String fieldName, ProfileValidation validation) {
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

        Profile profile = validation.validateProfileById(id);
        context.putNormalized("profile", profile);
        context.putNormalized(fieldName, id);
    }
}

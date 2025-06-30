package com.framework.framework.usermetric.validation.chain;

import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.InvalidDateException;
import com.framework.common.validation.DateValidation;
import com.framework.framework.usermetric.validation.MetricValidationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NotFutureDateValidation implements MetricValidationStep {

    private final String fieldName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public NotFutureDateValidation(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public void validate(MetricValidationContext context) {
        Object fieldValue = context.getOriginalRequest().getData().get(fieldName);

        if (fieldValue == null) {
            throw new BusinessException("Field '" + fieldName + "' is required.");
        }

        try {
            LocalDate date = LocalDate.parse(fieldValue.toString(), formatter);
            DateValidation.notAllowFuture(date);
            context.putNormalized(fieldName, date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Field '" + fieldName + "' must be in the format dd-MM-yyyy.");
        }
    }
}

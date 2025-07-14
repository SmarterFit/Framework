package com.framework.framework.usermetric.handler.impl;

import com.framework.framework.usermetric.entity.generic.GenericMetricRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.handler.AbstractMetricHandler;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.NumericRangeValidation;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GenericMetricHandler extends AbstractMetricHandler {

    @Override
    protected MetricValidationContext validate(MetricDataDTO request, MetricType metricType) {
        MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
                new RequiredFieldValidation("value"),
                new NumericRangeValidation("value")));

        return chain.execute(request, metricType);
    }

    @Override
    protected List<String> analyze(MetricValidationContext context) {
        return List.of();
    }

    @Override
    protected AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source) {
        GenericMetricRecord record = new GenericMetricRecord();

        double value = context.getNormalized("value", Double.class);

        record.setValue(value);
        record.setMetricType(context.getMetricType());
        record.setSource(source);
        record.setProfile(profile);

        return record;
    }

    @Override
    public MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record) {
        GenericMetricRecord genericRecord = (GenericMetricRecord) record;

        Map<String, Object> data = Map.of(
                "value", genericRecord.getValue());

        return new MetricDataResponseDTO(
                genericRecord.getId(),
                genericRecord.getMetricType().getType(),
                data,
                record.getCreatedAt()
        );
    }

    @Override
    public boolean supports(String metricType) {
        return "GENERIC_TYPE".equalsIgnoreCase(metricType);
    }

    @Override
    public String getSupportedType() {
        return "GENERIC_TYPE";
    }
}

package com.framework.framework.usermetric.handler.impl;

import com.framework.framework.usermetric.entity.WeightMetricRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.handler.AbstractMetricHandler;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.NotFutureDateValidation;
import com.framework.framework.usermetric.validation.chain.NumericRangeValidation;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class WeightMetricHandler extends AbstractMetricHandler {

    public List<String> alerts;

    @Override
    protected MetricValidationContext validate(MetricDataDTO request, MetricType metricType) {
        MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
                new RequiredFieldValidation("weight"),
                new RequiredFieldValidation("measurementDate"),
                new NumericRangeValidation("weight"),
                new NotFutureDateValidation("measurementDate")
        ));

        return chain.execute(request, metricType);
    }

    @Override
    protected List<String> analyze(MetricValidationContext context) {
        return List.of();
    }

    @Override
    protected AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source) {
        WeightMetricRecord record = new WeightMetricRecord();

        double weight = context.getNormalized("weight", Double.class);
        LocalDate measurementDate = context.getNormalized("measurementDate", LocalDate.class);

        record.setWeight(weight);
        record.setMeasurementDate(measurementDate);

        record.setMetricType(context.getMetricType());
        record.setSource(source);
        record.setProfile(profile);

        return record;
    }

    @Override
    public MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record) {
        WeightMetricRecord weightRecord = (WeightMetricRecord) record;

        Map<String, Object> data = Map.of(
                "Peso", weightRecord.getWeight(),
                "Data", weightRecord.getMeasurementDate()
        );

        return new MetricDataResponseDTO(
                weightRecord.getId(),
                weightRecord.getMetricType().getType(),
                data,
                record.getCreatedAt()
        );
    }

    @Override
    public boolean supports(String metricType) {
        return getSupportedType().equalsIgnoreCase(metricType);
    }

    @Override
    public String getSupportedType() {
        return "WEIGHT";
    }


}

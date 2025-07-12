package com.framework.framework.usermetric.handler;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.entity.MetricProcessResult;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractMetricHandler implements MetricHandler {

    @Override
    public MetricProcessResult handle(MetricDataDTO request, MetricType metricType, Profile profile, String source) {
        MetricValidationContext context = validate(request, metricType);
        afterValidation(context);
        List<String> alerts = analyze(context);
        AbstractMetricRecord record = build(context, profile, source);
        MetricDataResponseDTO responseDTO = toResponseDTO(record);
        return new MetricProcessResult(record, responseDTO, alerts);
    }

    protected abstract MetricValidationContext validate(MetricDataDTO request, MetricType metricType);

    protected abstract List<String> analyze(MetricValidationContext context);

    protected abstract AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source);

    @Override
    public abstract MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record);

    @Override
    public abstract String getSupportedType();

    public void afterValidation(MetricValidationContext context) {
        // no-op
    }
}

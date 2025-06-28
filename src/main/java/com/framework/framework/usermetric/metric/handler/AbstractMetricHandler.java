package com.framework.framework.usermetric.metric.handler;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.useraccess.entity.Profile;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public abstract class  AbstractMetricHandler implements MetricHandler{

    public List<String> alerts;

    @Override
    public AbstractMetricRecord handle(MetricDataDTO request, MetricType metricType, Profile profile, String source) {
        MetricValidationContext context = validate(request, metricType);
        alerts = analyze(context);
        return build(context, profile, source);

    }

    protected abstract MetricValidationContext validate(MetricDataDTO request, MetricType metricType);

    protected abstract List<String> analyze(MetricValidationContext context);

    protected abstract AbstractMetricRecord build(MetricValidationContext context,Profile profile, String source);

    @Override
    public List<String> alerts() {
        return alerts;
    }

}

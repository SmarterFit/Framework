package com.framework.framework.usermetric.handler;

import com.framework.framework.usermetric.dto.MetricProcessResult;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public interface MetricHandler {
    MetricProcessResult handle(MetricDataDTO metricDataRequest, MetricType metricType, Profile profile, String source);

    boolean supports(String metricType);

    MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record);

    String getSupportedType();

    String getUnit();

    double getMinThreshold();

    double getMaxThreshold();
}

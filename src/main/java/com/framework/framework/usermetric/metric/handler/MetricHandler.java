package com.framework.framework.usermetric.metric.handler;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MetricHandler {
    AbstractMetricRecord handle (MetricDataDTO metricDataRequest, MetricType metricType, Profile profile, String source);

    boolean supports(String metricType);

    List<String> alerts();

}

package com.framework.framework.usermetric.dto;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricProcessResult {
    private AbstractMetricRecord record;
    private MetricDataResponseDTO response;
    private List<String> alerts;
}

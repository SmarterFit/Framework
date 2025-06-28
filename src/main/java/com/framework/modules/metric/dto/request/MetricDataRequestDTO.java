package com.framework.modules.metric.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class MetricDataRequestDTO {

    @NotBlank(message = "Metric type is required")
    private String metricType;

    @NotBlank(message = "Source is required")
    private String source;

    @NotNull(message = "Data cannot be null")
    private Map<String, Object> data;
}

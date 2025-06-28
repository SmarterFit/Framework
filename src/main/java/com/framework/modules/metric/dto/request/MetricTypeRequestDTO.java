package com.framework.modules.metric.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MetricTypeRequestDTO {

    @NotBlank(message = "Metric type is required")
    private String type;

    @NotBlank(message = "Metric type description is required")
    private String unit;

    @NotNull(message = "min threshold is required")
    private double minThreshold;

    @NotNull(message = "max threshold is required")
    private double maxThreshold;
}

package com.framework.modules.metric.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricTypeResponseDTO {
    private UUID id;
    private String type;
    private String unit;
    private double minThreshold;
    private double maxThreshold;
    private boolean enabled;
}

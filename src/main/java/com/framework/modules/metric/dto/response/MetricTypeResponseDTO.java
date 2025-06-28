package com.framework.modules.metric.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class MetricTypeResponseDTO {
    private UUID id;
    private String type;
    private String unit;
    private double minThreshold;
    private double maxThreshold;
    private boolean enabled;
    private LocalDateTime createdAt;
}

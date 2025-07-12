package com.framework.modules.metric.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricDataResponseDTO {
    private UUID id;
    private String metricType; // Nome ou código da métrica (ex: "GRADE", "WEIGHT")
    private Map<String, Object> data; // Dados específicos (exemplo: peso, altura, nota, etc)
    private LocalDateTime createdAt; // Data de criação do registro
}

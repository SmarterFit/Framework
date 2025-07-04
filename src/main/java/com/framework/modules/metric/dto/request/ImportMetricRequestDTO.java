package com.framework.modules.metric.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ImportMetricRequestDTO {
    @NotBlank(message = "Source type cannot be blank")
    private String sourceType;

    @NotBlank(message = "File name cannot be blank")
    private String metricType;
}

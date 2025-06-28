package com.framework.modules.metric.dto.request;

import lombok.*;

import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MetricDataDTO {
    private Map<String, Object> data;

}

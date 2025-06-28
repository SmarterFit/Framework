package com.framework.framework.usermetric.validation;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MetricValidationContext {

    private final MetricDataDTO originalRequest;
    private final MetricType metricType;

    private final Map<String, Object> normalizedValues = new HashMap<>();

    public MetricValidationContext(MetricDataDTO originalRequest, MetricType metricType) {
        this.originalRequest = originalRequest;
        this.metricType = metricType;
    }

    public void putNormalized(String key, Object value) {
        normalizedValues.put(key, value);
    }

    public <T> T getNormalized(String key, Class<T> type) {
        Object value = normalizedValues.get(key);
        if (value == null) return null;
        return type.cast(value);
    }

    public Map<String, Object> getAllNormalized() {
        return normalizedValues;
    }

}

package com.framework.modules.metric.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricTypeRequestDTO;
import com.framework.modules.metric.dto.response.MetricTypeResponseDTO;

public class MetricTypeMapper {

    private MetricTypeMapper() {
        // Private constructor to prevent instantiation
    }

    public static MetricType  toEntity(MetricTypeRequestDTO dto) {
        return toEntity(dto, new MetricType());
    }

    public static MetricType toEntity(MetricTypeRequestDTO dto, MetricType metricType) {
        if (metricType == null) {
            throw new IllegalArgumentException("MetricType cannot be null");
        }
        return GenericMapper.map(dto, metricType);

    }

    public static MetricTypeResponseDTO toResponse(MetricType metricType) {
        if (metricType == null) {
            throw new IllegalArgumentException("MetricType cannot be null");
        }
        return GenericMapper.map(metricType, MetricTypeResponseDTO.class);
    }



}

package com.framework.framework.usermetric.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.usermetric.entity.MetricTypeResponseDTO;
import com.framework.framework.usermetric.entity.generic.MetricType;

public class MetricTypeMapper {
   private MetricTypeMapper() {
      // Private constructor to prevent instantiation
   }

   public static MetricTypeResponseDTO toResponse(MetricType metricType) {
      if (metricType == null) {
         return null;
      }

      return GenericMapper.map(metricType, MetricTypeResponseDTO.class);
   }
}

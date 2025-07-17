package com.framework.framework.usermetric.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MetricTypeResponseDTO {
   private UUID id;
   private String type;
   private String unit;
   private boolean enabled;
   private double minThreshold;
   private double maxThreshold;
}

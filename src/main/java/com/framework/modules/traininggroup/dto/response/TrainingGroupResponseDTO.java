package com.framework.modules.traininggroup.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.framework.common.enums.TrainingGroupType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrainingGroupResponseDTO {
   private UUID id;
   private String name;
   private String slug;
   private TrainingGroupType type;
   private LocalDateTime startDate;
   private LocalDateTime endDate;
}

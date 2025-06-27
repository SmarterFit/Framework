package com.framework.modules.traininggroup.dto.request;

import java.util.List;
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
public class SearchTrainingGroupRequestDTO {
      private String nameTerm;
      private UUID userId;
      private List<TrainingGroupType> types;
      private Boolean includeEnded;
      private Boolean includeNotStarted;
}

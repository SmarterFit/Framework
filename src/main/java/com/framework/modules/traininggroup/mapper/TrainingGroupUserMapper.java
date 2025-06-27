package com.framework.modules.traininggroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;
import com.framework.modules.traininggroup.entity.TrainingGroupUser;
import com.framework.modules.useraccess.mapper.UserMapper;

public class TrainingGroupUserMapper {
   private TrainingGroupUserMapper() {
      // Private constructor to prevent instantiation
   }

   public static TrainingGroupUserResponseDTO toResponse(TrainingGroupUser trainingGroupUser) {
      if (trainingGroupUser == null) {
         throw new ResourceNotFoundException("TrainingGroupUser not found.");
      }

      TrainingGroupUserResponseDTO response = GenericMapper.map(trainingGroupUser, TrainingGroupUserResponseDTO.class);
      response = response.toBuilder().user(UserMapper.toResponse(trainingGroupUser.getUser())).build();

      return response;
   }
}

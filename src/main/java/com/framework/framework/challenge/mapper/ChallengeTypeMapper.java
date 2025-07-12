package com.framework.framework.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.challenge.dto.response.ChallengeTypeResponseDTO;
import com.framework.framework.challenge.entity.ChallengeType;

public class ChallengeTypeMapper {
   private ChallengeTypeMapper() {
      // Private constructor to prevent instantiation
   }

   public static ChallengeTypeResponseDTO toResponse(ChallengeType challengeType) {
      if (challengeType == null) {
         return null;
      }

      return GenericMapper.map(challengeType, ChallengeTypeResponseDTO.class);
   }
}

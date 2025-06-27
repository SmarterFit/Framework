package com.framework.framework.gamification.processor;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.dto.response.GamificationEventResponseDTO;

public abstract class GamificationEventProcessor {
   public final GamificationEventResponseDTO process(GamificationEventRequestDTO request) {
      if (!validate(request)) {
         return GamificationEventResponseDTO.builder()
               .success(false)
               .build();
      }

      int basePoints = calculateBasePoints(request);
      int bonusPoints = calculateBonusPoints(request);

      return GamificationEventResponseDTO.builder()
            .success(true)
            .points(basePoints + bonusPoints)
            .build();
   }

   public abstract String getEventType();

   protected abstract boolean validate(GamificationEventRequestDTO request);

   protected abstract int calculateBasePoints(GamificationEventRequestDTO request);

   protected abstract int calculateBonusPoints(GamificationEventRequestDTO request);
}

package com.framework.framework.gamification.processor.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.processor.GamificationEventProcessor;

@Component
public class LoginEventProcessor extends GamificationEventProcessor {
   private static final String EVENT_TYPE = "login";
   private static final int BASE_POINTS = 1;
   private static final float BONUS_POINTS = 0.5f;
   private static final int BONUS_LIMIT = 5;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      return true;
   }

   @Override
   protected int calculateBasePoints(GamificationEventRequestDTO request) {
      return BASE_POINTS;
   }

   @Override
   protected int calculateBonusPoints(GamificationEventRequestDTO request) {
      Object userCreatedAt = request.getDetails().get("userCreatedAt");

      if (userCreatedAt != null && userCreatedAt instanceof LocalDateTime) {
         LocalDateTime date = (LocalDateTime) userCreatedAt;
         long daysRegistered = ChronoUnit.DAYS.between(date, LocalDateTime.now());
         return (int) Math.min(daysRegistered * BONUS_POINTS, BONUS_LIMIT);
      }

      return 0;
   }
}

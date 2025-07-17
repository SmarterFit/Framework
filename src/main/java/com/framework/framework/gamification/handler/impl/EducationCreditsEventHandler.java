package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class EducationCreditsEventHandler extends GamificationEventHandler {
   private static final String EVENT_TYPE = "EDUCATION_CREDITS";
   private static final int BASE_POINTS = 1;
   private static final float BONUS_POINTS = 0.05f;
   private static final int BONUS_LIMIT = 5;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      Object hours = request.getDetails().get("hours");

      if (hours == null) {
         this.message = "hours is required";
         return false;
      } else if (!(hours instanceof Double)) {
         this.message = "hours must be a double";
         return false;
      } else if ((Double) hours < 0 || (Double) hours > 10) {
         this.message = "grade must be between 0 and 10";
         return false;
      }

      return true;
   }

   @Override
   protected int calculateBasePoints(GamificationEventRequestDTO request) {
      return BASE_POINTS;
   }

   @Override
   protected int calculateBonusPoints(GamificationEventRequestDTO request) {
      Object hours = request.getDetails().get("hours");
      float vantage = Math.min(((Number) hours).floatValue() * BONUS_POINTS, BONUS_LIMIT);
      return Math.round(vantage);
   }
}
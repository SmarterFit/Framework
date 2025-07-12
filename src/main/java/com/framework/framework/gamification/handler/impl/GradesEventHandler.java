package com.framework.framework.gamification.handler.impl;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

public class GradesEventHandler extends GamificationEventHandler {
   private static final String EVENT_TYPE = "grades";
   private static final int BASE_POINTS = 0;
   private static final float BONUS_POINTS = 0.75f;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      Object grade = request.getDetails().get("grade");

      if (grade == null) {
         this.message = "grade is required";
         return false;
      } else if (!(grade instanceof Float)) {
         this.message = "grade must be a float";
         return false;
      } else if ((Float) grade < 0 || (Float) grade > 10) {
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
      Object grade = request.getDetails().get("grade");
      float vantage = (float) grade * BONUS_POINTS;
      return Math.round(vantage);
   }
}

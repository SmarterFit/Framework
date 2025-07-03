package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class CheckInEventHandler extends GamificationEventHandler {
   private final static String EVENT_TYPE = "check-in";
   private final static int BASE_POINTS = 1;

   // VANTAGE_A e VANTAGE_B foram obtidos resolvendo o sistema:
   // f(x) = a·x² + b·x
   // f(1) = a + b = 1
   // f(7) = 49a + 7b = 20
   // Isso dá a ≈ 0.3095 e b ≈ 0.6905, que aqui foram arredondados para 0.31 e
   // 0.69:
   private final static float VANTAGE_A = 0.31f;
   private final static float VANTAGE_B = 0.69f;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      Object firstCheckInToday = request.getDetails().get("firstCheckInToday");

      if (firstCheckInToday == null) {
         this.message = "firstCheckInToday is required";
         return false;
      } else if (!(firstCheckInToday instanceof Boolean)) {
         this.message = "firstCheckInToday must be a boolean";
         return false;
      } else if (!((Boolean) firstCheckInToday)) {
         this.message = "firstCheckInToday must be true";
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
      Object streak = request.getDetails().get("streak");

      if (streak != null && streak instanceof Integer) {
         Integer currentStreak = (Integer) streak;
         float vantage = currentStreak * currentStreak * VANTAGE_A + currentStreak * VANTAGE_B;
         return currentStreak + (int) Math.round(vantage);
      }

      return 0;
   }
}

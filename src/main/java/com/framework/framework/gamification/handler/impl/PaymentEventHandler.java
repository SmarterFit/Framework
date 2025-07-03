package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class PaymentEventHandler extends GamificationEventHandler {
   private final static String EVENT_TYPE = "payment-processed";
   private final static int BASE_POINTS = 5;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      Object paymentSuccess = request.getDetails().get("paymentSuccess");

      if (paymentSuccess == null) {
         this.message = "paymentSuccess is required";
         return false;
      } else if (!(paymentSuccess instanceof Boolean)) {
         this.message = "paymentSuccess must be a boolean";
         return false;
      } else if (!(Boolean) paymentSuccess) {
         this.message = "paymentSuccess must be true";
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
      return 0;
   }
}

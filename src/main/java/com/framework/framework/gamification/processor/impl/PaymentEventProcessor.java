package com.framework.framework.gamification.processor.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.processor.GamificationEventProcessor;

@Component
public class PaymentEventProcessor extends GamificationEventProcessor {
   private final static String EVENT_TYPE = "payment-processed";
   private final static int BASE_POINTS = 5;

   @Override
   public String getEventType() {
      return EVENT_TYPE;
   }

   @Override
   protected boolean validate(GamificationEventRequestDTO request) {
      Object paymentSuccess = request.getDetails().get("paymentSuccess");

      return paymentSuccess != null && paymentSuccess instanceof Boolean && (Boolean) paymentSuccess;
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

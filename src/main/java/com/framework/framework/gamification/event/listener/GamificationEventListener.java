package com.framework.framework.gamification.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.dto.response.GamificationEventResponseDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.framework.gamification.handler.GamificationEventHandler;
import com.framework.framework.gamification.registry.GamificationRegistry;
import com.framework.modules.traininggroup.service.TrainingGroupUserService;

@Component
public class GamificationEventListener {
   private final GamificationRegistry gamificationRegistry;
   private final TrainingGroupUserService trainingGroupUserService;

   public GamificationEventListener(GamificationRegistry gamificationRegistry,
         TrainingGroupUserService trainingGroupUserService) {
      this.gamificationRegistry = gamificationRegistry;
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   @EventListener
   public void onGamificationEvent(GamificationEvent event) {
      if (gamificationRegistry.isEnabled()) {
         GamificationEventRequestDTO request = event.getRequest();
         GamificationEventHandler handler = gamificationRegistry.getHandler(request.getEventType());

         if (handler != null) {
            GamificationEventResponseDTO response = handler.handle(request);

            if (response.isSuccess()) {
               trainingGroupUserService.updatePoints(request.getUserId(), response.getPoints());
               /// Enviar notificação
            }
         }
      }
   }
}

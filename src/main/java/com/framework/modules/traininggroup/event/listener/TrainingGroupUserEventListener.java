package com.framework.modules.traininggroup.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.framework.modules.traininggroup.event.TrainingGroupRestartedEvent;
import com.framework.modules.traininggroup.service.TrainingGroupUserService;

@Component
public class TrainingGroupUserEventListener {
   private final TrainingGroupUserService trainingGroupUserService;

   public TrainingGroupUserEventListener(TrainingGroupUserService trainingGroupUserService) {
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @EventListener
   public void onTrainingGroupRestarted(TrainingGroupRestartedEvent event) {
      trainingGroupUserService.resetPointsByTrainingGroupId(event.getTrainingGroup().getId());
   }
}

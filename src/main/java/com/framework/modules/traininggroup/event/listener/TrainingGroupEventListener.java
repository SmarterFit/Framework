package com.framework.modules.traininggroup.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.framework.modules.traininggroup.event.LastParticipantRemovedEvent;
import com.framework.modules.traininggroup.service.TrainingGroupService;

@Component
public class TrainingGroupEventListener {
   private final TrainingGroupService trainingGroupService;

   public TrainingGroupEventListener(TrainingGroupService trainingGroupService) {
      this.trainingGroupService = trainingGroupService;
   }

   @EventListener
   public void onLastParticipantRemoved(LastParticipantRemovedEvent event) {
      trainingGroupService.deleteTrainingGroup(event.getTrainingGroup());
   }
}

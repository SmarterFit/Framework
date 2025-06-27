package com.framework.modules.checkin.event.listener;

import com.framework.modules.checkin.event.CalculatePointsUserEvent;
import com.framework.modules.traininggroup.service.TrainingGroupUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CalculatePointsUserEventListener {
    private final TrainingGroupUserService trainingGroupUserService;

    public CalculatePointsUserEventListener(TrainingGroupUserService trainingGroupUserService) {
        this.trainingGroupUserService = trainingGroupUserService;
    }

    @EventListener
    public void calculatePointsUser(CalculatePointsUserEvent event) {
        trainingGroupUserService.updatePoints(event.getUserId(), event.getPoints());
    }
}

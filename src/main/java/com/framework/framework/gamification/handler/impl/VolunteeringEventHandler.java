package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class VolunteeringEventHandler extends GamificationEventHandler {

    private static final String EVENT_TYPE = "volunteering";
    private static final int BASE_POINTS = 20;

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    protected boolean validate(GamificationEventRequestDTO request) {
        if (!request.getDetails().containsKey("organization")) {
            this.message = "organization is required";
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
        if (hours instanceof Integer && (Integer) hours >= 5) {
            return 10;
        }
        return 0;
    }
}

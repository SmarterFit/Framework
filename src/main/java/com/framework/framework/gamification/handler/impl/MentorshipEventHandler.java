package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class MentorshipEventHandler extends GamificationEventHandler {

    private static final String EVENT_TYPE = "mentorship";
    private static final int BASE_POINTS = 15;

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    protected boolean validate(GamificationEventRequestDTO request) {
        if (!request.getDetails().containsKey("menteeId")) {
            this.message = "menteeId is required";
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
        Object duration = request.getDetails().get("durationMinutes");
        if (duration instanceof Integer && (Integer) duration >= 60) {
            return 10;
        }
        return 0;
    }
}

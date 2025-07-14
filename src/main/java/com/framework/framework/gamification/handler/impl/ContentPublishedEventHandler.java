package com.framework.framework.gamification.handler.impl;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.handler.GamificationEventHandler;

@Component
public class ContentPublishedEventHandler extends GamificationEventHandler {

    private static final String EVENT_TYPE = "content-published";
    private static final int BASE_POINTS = 10;

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    protected boolean validate(GamificationEventRequestDTO request) {
        if (!request.getDetails().containsKey("contentId")) {
            this.message = "contentId is required";
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
        Object views = request.getDetails().get("views");
        if (views instanceof Integer && (Integer) views > 1000) {
            return 5;
        }
        return 0;
    }
}

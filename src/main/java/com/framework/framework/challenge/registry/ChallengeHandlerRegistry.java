package com.framework.framework.challenge.registry;

import com.framework.framework.challenge.handle.ChallengeHandler;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChallengeHandlerRegistry {

    private final Map<String, ChallengeHandler> activeHandlers = new HashMap<>();
    private final List<ChallengeHandler> handlers;

    public ChallengeHandlerRegistry(List<ChallengeHandler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void init() {
        initializeHandlers();
    }

    private void initializeHandlers() {
        for (ChallengeHandler handler : handlers) {
            String challengeType = handler.getChallengeTypeId();

            if (activeHandlers.containsKey(challengeType)) {
                throw new IllegalStateException("Duplicate ChallengeHandler for metric: " + challengeType);
            }

            activeHandlers.put(challengeType, handler);
        }
    }

    public Optional<ChallengeHandler> getHandler(String metricTypeId) {
        return Optional.ofNullable(activeHandlers.get(metricTypeId));
    }

    public List<String> getSupportedMetricTypes() {
        return List.copyOf(activeHandlers.keySet());
    }

    public Map<String, ChallengeHandler> getActiveHandlers() {
        return activeHandlers;
    }

    public boolean isSupported(String metricTypeId) {
        return activeHandlers.containsKey(metricTypeId);
    }
}

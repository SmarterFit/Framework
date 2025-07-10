package com.framework.framework.challenge.validation;

import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.challenge.registry.ChallengeHandlerRegistry;
import org.springframework.stereotype.Component;

@Component
public class ChallengeHandleValidation {

    private final ChallengeHandlerRegistry registry;

    public ChallengeHandleValidation(ChallengeHandlerRegistry registry) {
        this.registry = registry;
    }

    public ChallengeHandler getHandler(String challengeType) {
        return  registry.getHandler(challengeType).orElseThrow( () ->
            new IllegalArgumentException("No handler found for challenge type: " + challengeType));

    }
}

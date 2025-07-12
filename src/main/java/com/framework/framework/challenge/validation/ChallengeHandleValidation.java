package com.framework.framework.challenge.validation;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.framework.challenge.entity.ChallengeType;
import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.challenge.registry.ChallengeHandlerRegistry;
import com.framework.framework.challenge.repository.ChallengeTypeRepository;

import org.springframework.stereotype.Component;

@Component
public class ChallengeHandleValidation {

    private final ChallengeHandlerRegistry registry;
    private final ChallengeTypeRepository challengeTypeRepository;

    public ChallengeHandleValidation(ChallengeHandlerRegistry registry,
            ChallengeTypeRepository challengeTypeRepository) {
        this.registry = registry;
        this.challengeTypeRepository = challengeTypeRepository;
    }

    public ChallengeHandler getHandler(String challengeTypeId) {
        return registry.getHandler(challengeTypeId).orElseThrow(
                () -> new ResourceNotFoundException("No handler found for challenge type: " + challengeTypeId));
    }

    public ChallengeType validateEnabledChallengeTypeById(String challengeTypeId) {
        return challengeTypeRepository.findByIdAndEnabledTrue(challengeTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge type not found"));
    }
}

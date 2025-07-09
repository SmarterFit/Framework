package com.framework.modules.challenge.validation;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.challenge.repository.ChallengeStepRepository;
import com.framework.modules.challenge.entity.ChallengeStep;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChallengeStepValidation {

    private final ChallengeStepRepository challengeStepRepository;

    public ChallengeStepValidation(ChallengeStepRepository challengeStepRepository) {
        this.challengeStepRepository = challengeStepRepository;
    }

    public ChallengeStep validateChallengeStep(UUID stepId) {
        return challengeStepRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge step not found with ID: " + stepId));
    }

    public void validateChallengeStepExists(UUID stepId) {
        if(!challengeStepRepository.existsById(stepId)){
            throw new ResourceNotFoundException("Challenge step not found with ID: " + stepId);
        }
    }
}

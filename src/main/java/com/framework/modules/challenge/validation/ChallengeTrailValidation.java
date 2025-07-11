package com.framework.modules.challenge.validation;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.challenge.repository.ChallengeTrailRepository;
import com.framework.modules.challenge.entity.ChallengeTrail;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChallengeTrailValidation {

    private final ChallengeTrailRepository challengeTrailRepository;

    public ChallengeTrailValidation(ChallengeTrailRepository challengeTrailRepository) {
        this.challengeTrailRepository = challengeTrailRepository;
    }

    public ChallengeTrail validateChallengerTail(UUID challengeTrailId) {
        return challengeTrailRepository.findById(challengeTrailId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge trail not found with ID: " + challengeTrailId));
    }

    public void validateChallengeTrailExists(UUID challengeTrailId) {
        if (!challengeTrailRepository.existsById(challengeTrailId)) {
            throw new ResourceNotFoundException("Challenge trail not found with ID: " + challengeTrailId);
        }
    }

    public boolean existsTrailByQuestId(UUID challengeQuestId) {
        return challengeTrailRepository.existsByChallengeQuestId(challengeQuestId);
    }

    public ChallengeTrail findByChallengeQuestId(UUID challengeQuestId) {
        return challengeTrailRepository.findByChallengeQuestId(challengeQuestId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge trail not found for quest ID: " + challengeQuestId));
    }
}

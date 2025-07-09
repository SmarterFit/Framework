package com.framework.modules.challenge.validation;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.challenge.repository.ChallengeDayRepository;
import com.framework.modules.challenge.entity.ChallengeDay;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class ChallengeDayValidation {

    private final ChallengeDayRepository challengeDayRepository;

    public ChallengeDayValidation(ChallengeDayRepository challengeDayRepository) {
        this.challengeDayRepository = challengeDayRepository;
    }

    public ChallengeDay validateChallengeDay(UUID challengeDayId) {
        return challengeDayRepository.findById(challengeDayId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge day not found with ID: " + challengeDayId));
    }

    public void validateChallengeDayExists(UUID challengeDayId) {
        if (!challengeDayRepository.existsById(challengeDayId)) {
            throw new ResourceNotFoundException("Challenge day not found with ID: " + challengeDayId);
        }
    }

    public void alreadyExistChallengeDay(LocalDate date){
        if (challengeDayRepository.existsByDate(date)) {
            throw new ResourceNotFoundException("A challenge day already exists for the date: " + date);
        }
    }
}

package com.framework.modules.challenge.validation;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.validation.DateValidation;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.repository.ChallengeQuestRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class ChallengeQuestValidation {

    private final ChallengeQuestRepository repository;

    public ChallengeQuestValidation(ChallengeQuestRepository repository) {
        this.repository = repository;
    }

    public void validateChallengeQuestDates(LocalDate startDate, LocalDate endDate) {
        DateValidation.validateDateRange(startDate, endDate, Boolean.TRUE);
    }

    public ChallengeQuest validateChallengeQuestById(UUID id) {
        return  repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge quest not found."));
    }

}

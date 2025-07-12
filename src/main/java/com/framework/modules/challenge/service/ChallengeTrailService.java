package com.framework.modules.challenge.service;

import com.framework.modules.challenge.dto.request.ChallengeTrailRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeTrailResponseDTO;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.challenge.mapper.ChallengeTrailMapper;
import com.framework.modules.challenge.repository.ChallengeTrailRepository;
import com.framework.modules.challenge.validation.ChallengeDayValidation;
import com.framework.modules.challenge.validation.ChallengeQuestValidation;
import com.framework.modules.challenge.validation.ChallengeTrailValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChallengeTrailService {

    private final ChallengeTrailRepository repository;
    private final ChallengeTrailValidation challengeTrailValidation;
    private final ChallengeDayValidation challengeDayValidation;
    private final ChallengeQuestValidation challengeQuestValidation;

    public ChallengeTrailService(ChallengeTrailRepository repository,
            ChallengeTrailValidation challengeTrailValidation,
            ChallengeDayValidation challengeDayValidation,
            ChallengeQuestValidation challengeQuestValidation) {
        this.repository = repository;
        this.challengeTrailValidation = challengeTrailValidation;
        this.challengeDayValidation = challengeDayValidation;
        this.challengeQuestValidation = challengeQuestValidation;
    }

    public ChallengeTrailResponseDTO create(ChallengeTrailRequestDTO request, UUID requesterId) {
        ChallengeQuest quest = challengeQuestValidation.validateChallengeQuestById(request.getChallengeQuestId());
        List<ChallengeDay> days = new ArrayList<>();

        if (request.getChallengeDayIds() != null) {
            days = request.getChallengeDayIds().stream()
                    .map(challengeDayValidation::validateChallengeDay)
                    .toList();
        }

        ChallengeTrail entity = ChallengeTrailMapper.toEntity(request, quest, days);
        ChallengeTrail saved = repository.save(entity);
        return ChallengeTrailMapper.toResponseDTO(saved);
    }

    public void delete(UUID id) {
        challengeTrailValidation.validateChallengeTrailExists(id);
        repository.deleteById(id);
    }

    public ChallengeTrailResponseDTO findById(UUID id) {
        ChallengeTrail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeTrail not found"));
        return ChallengeTrailMapper.toResponseDTO(entity);
    }

    public void deleteChallengeTrailByQuestId(UUID questId) {
        challengeQuestValidation.validateChallengeQuestById(questId);
        ChallengeTrail trail = challengeTrailValidation.findByChallengeQuestId(questId);
        repository.delete(trail);
    }

    public ChallengeTrailResponseDTO create(ChallengeTrail trail) {
        ChallengeTrail savedTrail = repository.save(trail);

        return ChallengeTrailMapper.toResponseDTO(savedTrail);
    }

}
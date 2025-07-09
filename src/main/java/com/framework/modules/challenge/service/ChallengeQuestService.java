package com.framework.modules.challenge.service;


import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.dto.request.ChallengeQuestRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeQuestResponseDTO;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.mapper.ChallengeQuestMapper;
import com.framework.modules.challenge.repository.ChallengeQuestRepository;
import com.framework.modules.challenge.validation.ChallengeQuestValidation;
import com.framework.modules.metric.validation.MetricTypeValidation;
import com.framework.modules.useraccess.entity.Profile;
import com.framework.modules.useraccess.validation.ProfileValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChallengeQuestService {

    private final ChallengeQuestRepository challengeQuestRepository;
    private final ChallengeQuestValidation challengeQuestValidation;
    private final ProfileValidation profileValidation;
    private final MetricTypeValidation metricTypeValidation;

    public ChallengeQuestService(
            ChallengeQuestRepository challengeQuestRepository,
            ChallengeQuestValidation challengeQuestValidation,
            ProfileValidation profileValidation,
            MetricTypeValidation metricTypeValidation
    ) {
        this.challengeQuestRepository = challengeQuestRepository;
        this.challengeQuestValidation = challengeQuestValidation;
        this.profileValidation = profileValidation;
        this.metricTypeValidation = metricTypeValidation;
    }

    @Transactional
    public ChallengeQuestResponseDTO createChallengeQuest(ChallengeQuestRequestDTO requestDTO, UUID requesterId) {
        Profile profile = profileValidation.validateProfileById(requesterId);
        challengeQuestValidation.validateChallengeQuestDates(requestDTO.getStartDate(), requestDTO.getEndDate());

        MetricType metricType = metricTypeValidation.validateMetricTypeById(requestDTO.getMetricTypeId());

        ChallengeQuest quest = ChallengeQuestMapper.toEntity(requestDTO, profile, metricType);
        quest.setCreatedAt(LocalDateTime.now());

        challengeQuestRepository.save(quest);

        return ChallengeQuestMapper.toResponse(quest);
    }

    @Transactional(readOnly = true)
    public ChallengeQuestResponseDTO getChallengeQuestById(UUID id) {
        ChallengeQuest quest = challengeQuestValidation.validateChallengeQuestById(id);
        return ChallengeQuestMapper.toResponse(quest);
    }



    @Transactional
    public ChallengeQuestResponseDTO updateChallengeQuest(UUID questId, ChallengeQuestRequestDTO requestDTO) {
        ChallengeQuest quest = challengeQuestValidation.validateChallengeQuestById(questId);

        challengeQuestValidation.validateChallengeQuestDates(requestDTO.getStartDate(), requestDTO.getEndDate());

        MetricType metricType = metricTypeValidation.validateMetricTypeById(requestDTO.getMetricTypeId());

        ChallengeQuest updated = ChallengeQuestMapper.toEntity(requestDTO, metricType, quest.getProfile(), quest);
        challengeQuestRepository.save(updated);

        return ChallengeQuestMapper.toResponse(updated);
    }

    @Transactional
    public void deleteChallengeQuest(UUID id) {
        ChallengeQuest quest = challengeQuestValidation.validateChallengeQuestById(id);
        challengeQuestRepository.delete(quest);
    }
}

package com.framework.modules.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.challenge.entity.ChallengeType;
import com.framework.framework.challenge.mapper.ChallengeTypeMapper;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.mapper.MetricTypeMapper;
import com.framework.modules.challenge.dto.request.ChallengeQuestRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeQuestResponseDTO;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.useraccess.entity.Profile;

public class ChallengeQuestMapper {

    private ChallengeQuestMapper() {
        // Private constructor to prevent instantiation
    }

    public static ChallengeQuest toEntity(ChallengeQuestRequestDTO dto, Profile profile, MetricType metricType,
            ChallengeType challengeType) {
        return toEntity(dto, metricType, profile, challengeType, new ChallengeQuest());
    }

    public static ChallengeQuest toEntity(ChallengeQuestRequestDTO dto, MetricType metricType, Profile profile,
            ChallengeType challengeType,
            ChallengeQuest challengeQuest) {
        challengeQuest = GenericMapper.map(dto, challengeQuest);
        challengeQuest.setMetricType(metricType);
        challengeQuest.setChallengeType(challengeType);
        challengeQuest.setProfile(profile);
        challengeQuest.setExperienceLevel(dto.getExperienceLevel());

        return challengeQuest;
    }

    public static ChallengeQuestResponseDTO toResponse(ChallengeQuest challengeQuest) {
        if (challengeQuest == null) {
            return null;
        }

        ChallengeQuestResponseDTO response = GenericMapper.map(challengeQuest, ChallengeQuestResponseDTO.class);
        response = response.toBuilder()
                .challengeType(ChallengeTypeMapper.toResponse(challengeQuest.getChallengeType()))
                .metricType(MetricTypeMapper.toResponse(challengeQuest.getMetricType()))
                .build();
        return response;
    }

}

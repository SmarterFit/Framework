package com.framework.modules.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.challenge.dto.request.ChallengeQuestRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeQuestResponseDTO;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.useraccess.entity.Profile;

public class ChallengeQuestMapper {

    private ChallengeQuestMapper(){
        // Private constructor to prevent instantiation
    }

    public static ChallengeQuest toEntity(ChallengeQuestRequestDTO dto, Profile profile, MetricType metricType) {
        return toEntity(dto, metricType, profile, new ChallengeQuest());
    }

    public static ChallengeQuest toEntity(ChallengeQuestRequestDTO dto, MetricType metricType, Profile profile, ChallengeQuest challengeQuest) {
        challengeQuest = GenericMapper.map(dto, challengeQuest);
        challengeQuest.setMetricType(metricType);
        challengeQuest.setProfile(profile);

        return challengeQuest;
    }


    public static ChallengeQuestResponseDTO toResponse(ChallengeQuest challengeQuest) {
        return ChallengeQuestResponseDTO.builder()
                .id(challengeQuest.getId())
                .metricType(challengeQuest.getMetricType())
                .title(challengeQuest.getTitle())
                .daysOfWeek(challengeQuest.getDaysOfWeek())
                .description(challengeQuest.getDescription())
                .startDate(challengeQuest.getStartDate())
                .endDate(challengeQuest.getEndDate())
                .challengeType(challengeQuest.getChallengeType())
                .build();
    }

}

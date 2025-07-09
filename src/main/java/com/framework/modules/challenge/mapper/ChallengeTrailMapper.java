package com.framework.modules.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.modules.challenge.dto.request.ChallengeTrailRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeTrailResponseDTO;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeQuest;
import com.framework.modules.challenge.entity.ChallengeTrail;

import java.util.List;
import java.util.stream.Collectors;

public class ChallengeTrailMapper {

    private ChallengeTrailMapper() {
        // Private constructor to prevent instantiation
    }

    public static ChallengeTrail toEntity(ChallengeTrailRequestDTO dto, ChallengeQuest challengeQuest, List<ChallengeDay> days) {
        return toEntity(dto, challengeQuest, days, new ChallengeTrail());
    }

    public static ChallengeTrail toEntity(ChallengeTrailRequestDTO dto, ChallengeQuest challengeQuest, List<ChallengeDay> days,
                                          ChallengeTrail trail) {
        if (dto == null) return null;

        trail = GenericMapper.map(dto, trail);
        trail.setChallengeQuest(challengeQuest);

        if (days != null) {
            for (ChallengeDay day : days) {
                day.setTrail(trail);
            }
            trail.setDays(days);
        }
        return trail;
    }

    public static ChallengeTrailResponseDTO toResponseDTO(ChallengeTrail entity) {
        if (entity == null) return null;

        ChallengeQuest challengeQuest = entity.getChallengeQuest();
        return ChallengeTrailResponseDTO.builder()
                .id(entity.getId())
                .quest(ChallengeQuestMapper.toResponse(challengeQuest))
                .days(entity.getDays().stream()
                        .map(ChallengeDayMapper::toResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
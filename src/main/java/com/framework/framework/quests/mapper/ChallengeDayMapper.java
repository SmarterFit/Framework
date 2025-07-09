package com.framework.framework.quests.mapper;

import com.framework.framework.quests.dto.request.ChallengeDayRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeDayResponseDTO;
import com.framework.framework.quests.entity.ChallengeDay;

import java.util.stream.Collectors;

public class ChallengeDayMapper {

    public static ChallengeDayResponseDTO toResponseDTO(ChallengeDay entity) {
        if (entity == null) return null;

        return ChallengeDayResponseDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .steps(entity.getSteps().stream()
                        .map(ChallengeStepMapper::toResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ChallengeDay toEntity(ChallengeDayRequestDTO dto) {
        if (dto == null) return null;

        ChallengeDay day = new ChallengeDay();
        day.setDate(dto.getDate());

        var trail = new com.framework.framework.quests.entity.ChallengeTrail();
        trail.setId(dto.getTrailId());
        day.setTrail(trail);

        return day;
    }
}
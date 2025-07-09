package com.framework.modules.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestCreateDTO;
import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestUpdateDTO;
import com.framework.modules.challenge.dto.response.ChallengeDayResponseDTO;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeTrail;

import java.util.List;
import java.util.stream.Collectors;

public class ChallengeDayMapper {

    private ChallengeDayMapper() {
        // Private constructor to prevent instantiation
    }

    public static ChallengeDay toEntity(ChallengeDayRequestCreateDTO dto, List<ChallengeStep> steps, ChallengeTrail trail) {
        return toEntity(dto, trail, steps, new ChallengeDay());
    }

    public static ChallengeDay toEntity(ChallengeDayRequestCreateDTO dto, ChallengeTrail trail, List<ChallengeStep> steps,
                                        ChallengeDay day) {
        if (dto == null) return null;

        if(trail == null) throw new IllegalArgumentException("Trail cannot be null");

        day = GenericMapper.map(dto, day);
        day.setTrail(trail);

        if (steps != null) {
            for (ChallengeStep step : steps) {
                step.setDay(day);
            }
            day.setSteps(steps);
        }

        return day;
    }

    public static ChallengeDay toEntity(ChallengeDayRequestUpdateDTO dto, ChallengeDay day) {
        if (dto == null) return null;
        day.setDate(dto.getDate());
        return day;
    }

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


}
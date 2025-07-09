package com.framework.modules.challenge.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.modules.challenge.dto.request.ChallengeStepRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeStepResponseDTO;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeDay;

public class ChallengeStepMapper {

    public static ChallengeStepResponseDTO toResponseDTO(ChallengeStep entity) {
        if (entity == null) return null;

        return ChallengeStepResponseDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .completed(entity.isCompleted())
                .build();
    }

    public static ChallengeStep toEntity(ChallengeStepRequestDTO dto, ChallengeDay challengeDay) {
        return toEntity(dto, challengeDay, new ChallengeStep());
    }

    public static ChallengeStep toEntity(ChallengeStepRequestDTO dto, ChallengeDay challengeDay, ChallengeStep step) {
        if (dto == null) return null;

        if (challengeDay == null) {
            throw new IllegalArgumentException("Challenge day cannot be null");
        }

        step = GenericMapper.map(dto, step);
        step.setDay(challengeDay);
        return step;
    }
}
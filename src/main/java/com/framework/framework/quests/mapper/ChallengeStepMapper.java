package com.framework.framework.quests.mapper;

import com.framework.framework.quests.dto.request.ChallengeStepRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeStepResponseDTO;
import com.framework.framework.quests.entity.ChallengeStep;

public class ChallengeStepMapper {

    public static ChallengeStepResponseDTO toResponseDTO(ChallengeStep entity) {
        if (entity == null) return null;

        return ChallengeStepResponseDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .completed(entity.isCompleted())
                .build();
    }

    public static ChallengeStep toEntity(ChallengeStepRequestDTO dto) {
        if (dto == null) return null;

        ChallengeStep step = new ChallengeStep();
        step.setDescription(dto.getDescription());
        step.setCompleted(dto.isCompleted());

        var day = new com.framework.framework.quests.entity.ChallengeDay();
        day.setId(dto.getDayId());
        step.setDay(day);

        return step;
    }
}
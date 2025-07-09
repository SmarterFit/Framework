package com.framework.framework.quests.mapper;

import com.framework.framework.quests.dto.request.ChallengeTrailRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeTrailResponseDTO;
import com.framework.framework.quests.entity.ChallengeTrail;

import java.util.stream.Collectors;

public class ChallengeTrailMapper {

    public static ChallengeTrailResponseDTO toResponseDTO(ChallengeTrail entity) {
        if (entity == null) return null;

        return ChallengeTrailResponseDTO.builder()
                .id(entity.getId())
                .quest(PersonalQuestMapper.toResponseDTO(entity.getQuest()))
                .days(entity.getDays().stream()
                        .map(ChallengeDayMapper::toResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ChallengeTrail toEntity(ChallengeTrailRequestDTO dto) {
        if (dto == null) return null;

        ChallengeTrail trail = new ChallengeTrail();
        // Só setamos o quest pelo id (apenas a referência)
        var quest = new com.framework.framework.quests.entity.PersonalQuest();
        quest.setId(dto.getQuestId());
        trail.setQuest(quest);

        return trail;
    }
}
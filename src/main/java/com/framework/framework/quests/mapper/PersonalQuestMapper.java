package com.framework.framework.quests.mapper;


import com.framework.framework.quests.dto.request.PersonalQuestRequestDTO;
import com.framework.framework.quests.dto.response.PersonalQuestResponseDTO;
import com.framework.framework.quests.entity.PersonalQuest;

public class PersonalQuestMapper {

    public static PersonalQuestResponseDTO toResponseDTO(PersonalQuest entity) {
        if (entity == null) return null;

        return PersonalQuestResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .domain(entity.getDomain())
                .description(entity.getDescription())
                .weeklyFrequency(entity.getWeeklyFrequency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .metricType(entity.getMetricType())
                .completed(entity.isCompleted())
                .build();
    }

    public static PersonalQuest toEntity(PersonalQuestRequestDTO dto) {
        if (dto == null) return null;

        return PersonalQuest.builder()
                .name(dto.getName())
                .domain(dto.getDomain())
                .description(dto.getDescription())
                .weeklyFrequency(dto.getWeeklyFrequency())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .metricType(dto.getMetricType())
                .completed(dto.isCompleted())
                .build();
    }
}
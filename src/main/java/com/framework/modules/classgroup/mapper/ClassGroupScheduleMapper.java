package com.framework.modules.classgroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.classgroup.dto.request.classgroup.schedule.CreateClassGroupScheduleRequestDTO;
import com.framework.modules.classgroup.dto.response.ClassGroupScheduleResponseDTO;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.entity.ClassGroupSchedule;

public class ClassGroupScheduleMapper {
    private ClassGroupScheduleMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassGroupSchedule toEntity(CreateClassGroupScheduleRequestDTO dto,
            ClassGroup classGroup) {
        return toEntity(dto, classGroup, new ClassGroupSchedule());
    }

    public static ClassGroupSchedule toEntity(CreateClassGroupScheduleRequestDTO dto, ClassGroup classGroup,
            ClassGroupSchedule classGroupSchedule) {
        if (classGroupSchedule == null) {
            throw new ResourceNotFoundException("Class Group Schedule not found.");
        }
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }

        classGroupSchedule = GenericMapper.map(dto, classGroupSchedule);
        classGroupSchedule.setClassGroup(classGroup);

        return classGroupSchedule;
    }

    public static ClassGroupScheduleResponseDTO toResponse(ClassGroupSchedule classGroupSchedule) {
        if (classGroupSchedule == null) {
            throw new ResourceNotFoundException("Class Group Schedule not found.");
        }

        ClassGroupScheduleResponseDTO response = GenericMapper.map(classGroupSchedule,
                ClassGroupScheduleResponseDTO.class);
        response = response.toBuilder().classGroupId(classGroupSchedule.getClassGroup().getId()).build();

        return response;
    }
}

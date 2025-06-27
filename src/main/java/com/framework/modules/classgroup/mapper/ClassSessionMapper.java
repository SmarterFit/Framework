package com.framework.modules.classgroup.mapper;

import com.framework.common.enums.SessionStatus;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.framework.modules.classgroup.dto.request.classsession.UpdateClassSessionRequestDTO;
import com.framework.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.entity.ClassSession;

public class ClassSessionMapper {

    private ClassSessionMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassSession toEntity(CreateClassSessionRequestDTO dto, ClassGroup classGroup) {
        return toEntity(dto, classGroup, new ClassSession());
    }

    public static ClassSession toEntity(CreateClassSessionRequestDTO dto,
            ClassGroup classGroup, ClassSession classSession) {
        if (classSession == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }
        if (classGroup == null) {
            throw new ResourceNotFoundException("ClassGroup not found.");
        }

        classSession = GenericMapper.map(dto, classSession);
        classSession.setClassGroup(classGroup);
        classSession.setStatus(SessionStatus.CONFIRMED);

        return classSession;
    }

    public static ClassSession toEntity(UpdateClassSessionRequestDTO dto,
            ClassSession classSession) {
        if (classSession == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }
        classSession = GenericMapper.map(dto, classSession);
        return classSession;
    }

    /// TODO: Mudar para toResponse (padrão)
    public static ClassSessionResponseDTO toResponse(ClassSession classSession) {
        if (classSession == null) {
            throw new ResourceNotFoundException("ClassSession not found.");
        }

        ClassSessionResponseDTO response = GenericMapper.map(classSession, ClassSessionResponseDTO.class);
        response = response.toBuilder().classGroupId(classSession.getClassGroup().getId())
                .build();

        return response;
    }

}

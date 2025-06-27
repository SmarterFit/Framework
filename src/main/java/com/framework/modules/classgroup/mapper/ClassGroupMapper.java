package com.framework.modules.classgroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.classgroup.dto.request.classgroup.ClassGroupRequestDTO;
import com.framework.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.entity.Modality;
import com.framework.modules.useraccess.entity.User;

import java.util.stream.Collectors;

public class ClassGroupMapper {
    private ClassGroupMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassGroup toEntity(ClassGroupRequestDTO dto, Modality modality, User creator) {
        return toEntity(dto, modality, creator, new ClassGroup());
    }

    public static ClassGroup toEntity(ClassGroupRequestDTO dto, Modality modality,
                                      User creator, ClassGroup classGroup) {
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found.");
        }
        if (creator == null) {
            throw new ResourceNotFoundException("Creator not found.");
        }

        classGroup = GenericMapper.map(dto, classGroup);
        classGroup.setModality(modality);
        classGroup.setCreatedByUser(creator);

        return classGroup;
    }

    public static ClassGroupResponseDTO toResponse(ClassGroup classGroup, String nameCreator) {
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }

        ClassGroupResponseDTO response = GenericMapper.map(classGroup, ClassGroupResponseDTO.class);

        response = response.toBuilder()
                .modalityDTO(ModalityMapper.toResponse(classGroup.getModality()))
                .nameCreator(nameCreator)
                .schedulesDTO(classGroup.getSchedules().stream().map(ClassGroupScheduleMapper::toResponse).
                        collect(Collectors.toList()))
                .build();

        return response;
    }

    public static ClassGroupResponseDTO toResponse(ClassGroup classGroup) {
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }

        ClassGroupResponseDTO response = GenericMapper.map(classGroup, ClassGroupResponseDTO.class);

        response = response.toBuilder()
                .modalityDTO(ModalityMapper.toResponse(classGroup.getModality()))
                .schedulesDTO(classGroup.getSchedules().stream().map(ClassGroupScheduleMapper::toResponse).
                        collect(Collectors.toList()))
                .build();

        return response;
    }
}

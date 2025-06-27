package com.framework.modules.classgroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.classgroup.dto.response.classgroupuser.ClassUsersResponseDTO;
import com.framework.modules.classgroup.entity.ClassGroupUser;

public class ClassGroupUserMapper {

    private ClassGroupUserMapper() {
        // Private constructor to prevent instantiation
    }


    public static ClassUsersResponseDTO toResponse(ClassGroupUser classGroupUser) {
        if (classGroupUser == null) {
            throw new ResourceNotFoundException("ClassGroupUser not found.");
        }

        return ClassUsersResponseDTO.builder()
                .userId(classGroupUser.getUser().getId())
                .name(classGroupUser.getUser().getProfile().getFullName())
                .email(classGroupUser.getUser().getEmail())
                .isTeacher(classGroupUser.isTeacher())
                .build();
    }
}

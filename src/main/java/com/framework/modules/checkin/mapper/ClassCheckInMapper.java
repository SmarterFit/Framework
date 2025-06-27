package com.framework.modules.checkin.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.checkin.dto.request.ClassCheckInRequestDTO;
import com.framework.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.framework.modules.checkin.entity.ClassCheckIn;
import com.framework.modules.classgroup.entity.ClassSession;
import com.framework.modules.classgroup.mapper.ClassSessionMapper;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.mapper.UserMapper;

public class ClassCheckInMapper {
   private ClassCheckInMapper() {
      // Private constructor to prevent instantiation
   }

   public static ClassCheckIn toEntity(ClassCheckInRequestDTO dto, User user, ClassSession classSession) {
      return toEntity(dto, user, classSession, new ClassCheckIn());
   }

   public static ClassCheckIn toEntity(ClassCheckInRequestDTO dto, User user, ClassSession classSession,
         ClassCheckIn classCheckIn) {
      if (dto == null) {
         return null;
      } else if (user == null) {
         throw new ResourceNotFoundException("User cannot be null");
      } else if (classSession == null) {
         throw new ResourceNotFoundException("ClassSession cannot be null");
      } else if (classCheckIn == null) {
         throw new ResourceNotFoundException("ClassCheckIn cannot be null");
      }

      classCheckIn = GenericMapper.map(dto, classCheckIn);

      classCheckIn.setUser(user);
      classCheckIn.setClassSession(classSession);

      return classCheckIn;
   }

   public static ClassCheckInResponseDTO toResponse(ClassCheckIn classCheckIn) {
      if (classCheckIn == null) {
         throw new ResourceNotFoundException("ClassCheckIn cannot be null");
      }
      ClassCheckInResponseDTO response = GenericMapper.map(classCheckIn, ClassCheckInResponseDTO.class);

      User user = classCheckIn.getUser();
      ClassSession classSession = classCheckIn.getClassSession();

      response = response.toBuilder()
            .user(UserMapper.toResponse(user))
            .classSession(ClassSessionMapper.toResponse(classSession))
            .build();

      return response;
   }
}

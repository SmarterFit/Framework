package com.framework.modules.checkin.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.framework.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.framework.modules.checkin.entity.GymCheckIn;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.mapper.UserMapper;

public class GymCheckInMapper {
   private GymCheckInMapper() {
      // Private constructor to prevent instantiation
   }

   public static GymCheckIn toEntity(GymCheckInAndCheckOutRequestDTO dto, User user) {
      return toEntity(dto, user, new GymCheckIn());
   }

   public static GymCheckIn toEntity(GymCheckInAndCheckOutRequestDTO dto, User user, GymCheckIn gymCheckIn) {
      if (dto == null) {
         return null;
      } else if (user == null) {
         throw new ResourceNotFoundException("User cannot be null");
      } else if (gymCheckIn == null) {
         throw new ResourceNotFoundException("GymCheckIn cannot be null");
      }

      gymCheckIn.setUser(user);

      return gymCheckIn;
   }

   public static GymCheckInResponseDTO toResponse(GymCheckIn gymCheckIn) {
      if (gymCheckIn == null) {
         throw new ResourceNotFoundException("GymCheckIn cannot be null");
      }

      GymCheckInResponseDTO response = GenericMapper.map(gymCheckIn, GymCheckInResponseDTO.class);

      User user = gymCheckIn.getUser();

      response = response.toBuilder()
            .user(UserMapper.toResponse(user))
            .build();

      return response;
   }
}

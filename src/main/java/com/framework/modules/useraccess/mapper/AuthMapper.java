package com.framework.modules.useraccess.mapper;

import com.framework.common.dto.response.JwtToken;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.useraccess.dto.response.AuthResponseDTO;
import com.framework.modules.useraccess.entity.User;

public class AuthMapper {
   private AuthMapper() {
      // Private constructor to prevent instantiation
   }

   public static AuthResponseDTO toResponse(JwtToken accessToken, User user) {
      if (user == null) {
         throw new ResourceNotFoundException("User not found.");
      }

      return new AuthResponseDTO(accessToken, UserMapper.toResponse(user));
   }
}

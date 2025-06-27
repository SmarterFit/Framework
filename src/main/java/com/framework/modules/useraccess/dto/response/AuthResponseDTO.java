package com.framework.modules.useraccess.dto.response;

import com.framework.common.dto.response.JwtToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthResponseDTO {
      private JwtToken accessToken;
      private UserResponseDTO user;
}

package com.framework.modules.useraccess.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.dto.response.JwtToken;
import com.framework.common.util.JwtUtil;
import com.framework.common.util.SensitiveDataDecryptor;
import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.modules.useraccess.dto.request.user.LoginRequestDTO;
import com.framework.modules.useraccess.dto.response.AuthResponseDTO;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.mapper.AuthMapper;
import com.framework.modules.useraccess.validation.AuthValidation;

@Service
public class AuthService {
   private final AuthValidation authValidation;
   private final SensitiveDataDecryptor sensitiveDataDecryptor;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public AuthService(AuthValidation authValidation,
         SensitiveDataDecryptor sensitiveDataDecryptor,
         ApplicationEventPublisher publisher) {
      this.authValidation = authValidation;
      this.sensitiveDataDecryptor = sensitiveDataDecryptor;
      this.publisher = publisher;
   }

   @Transactional(readOnly = true)
   public AuthResponseDTO login(LoginRequestDTO requestDTO) {
      User user = authValidation.validateUser(requestDTO.getEmail(), requestDTO.getPassword());

      JwtToken accessToken = JwtUtil.generateToken(user.getId().toString());

      AuthResponseDTO response = AuthMapper.toResponse(accessToken, user);
      response.setUser(sensitiveDataDecryptor.decrypt(response.getUser()));

      GamificationEventRequestDTO dto = GamificationEventRequestDTO.builder()
            .eventType("login")
            .userId(user.getId())
            .details(Map.of("userCreatedAt", user.getCreatedAt()))
            .build();
      GamificationEvent event = new GamificationEvent(dto);
      publisher.publishEvent(event);

      return response;
   }
}

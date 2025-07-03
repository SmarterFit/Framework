package com.framework.modules.checkin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.enums.CheckInStatus;
import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.modules.checkin.dto.request.ClassCheckInRequestDTO;
import com.framework.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.framework.modules.checkin.entity.ClassCheckIn;
import com.framework.modules.checkin.entity.id.ClassCheckInId;
import com.framework.modules.checkin.mapper.ClassCheckInMapper;
import com.framework.modules.checkin.repository.ClassCheckInRepository;
import com.framework.modules.checkin.util.SensitiveCheckInDataDecryptor;
import com.framework.modules.checkin.validation.ClassCheckInValidation;
import com.framework.modules.classgroup.entity.ClassSession;
import com.framework.modules.classgroup.validation.ClassGroupUserValidation;
import com.framework.modules.classgroup.validation.ClassSessionValidation;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.validation.UserValidation;

@Service
public class ClassCheckInService {
   private final ClassCheckInRepository classCheckInRepository;
   private final ClassCheckInValidation classCheckInValidation;
   private final UserValidation userValidation;
   private final ClassSessionValidation classSessionValidation;
   private final ClassGroupUserValidation classGroupUserValidation;
   private final SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor;
   private final ApplicationEventPublisher publisher;

   private static final int STREAK_DAYS_RANGE = 7;

   @Autowired
   public ClassCheckInService(ClassCheckInRepository classCheckInRepository,
         ClassCheckInValidation classCheckInValidation, UserValidation userValidation,
         ClassSessionValidation classSessionValidation, ClassGroupUserValidation classGroupUserValidation,
         SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor,
         ApplicationEventPublisher publisher) {
      this.classCheckInRepository = classCheckInRepository;
      this.classCheckInValidation = classCheckInValidation;
      this.userValidation = userValidation;
      this.classSessionValidation = classSessionValidation;
      this.classGroupUserValidation = classGroupUserValidation;
      this.sensitiveCheckInDataDecryptor = sensitiveCheckInDataDecryptor;
      this.publisher = publisher;
   }

   @Transactional
   public ClassCheckInResponseDTO createClassCheckIn(ClassCheckInRequestDTO requestDTO) {
      User user = userValidation.validateUserById(requestDTO.getUserId());
      ClassSession classSession = classSessionValidation.validateClassSessionById(requestDTO.getClassSessionId());

      classGroupUserValidation.validateClassGroupUserId(user.getId(), classSession.getClassGroup().getId());

      ClassCheckInId classCheckInId = new ClassCheckInId(user.getId(), classSession.getId());
      classCheckInValidation.validateClassCheckInNotExists(classCheckInId);

      ClassCheckIn classCheckIn = ClassCheckInMapper.toEntity(requestDTO, user, classSession);

      if (requestDTO.getStatus() == CheckInStatus.PRESENT) {
         classCheckIn.setCheckInTime(LocalDateTime.now());
         sendClassCheckInEvents(user);
      }

      classCheckIn = classCheckInRepository.save(classCheckIn);

      return sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn));
   }

   @Transactional
   public ClassCheckInResponseDTO updateClassCheckIn(ClassCheckInRequestDTO requestDTO) {
      ClassCheckInId classCheckInId = new ClassCheckInId(requestDTO.getUserId(), requestDTO.getClassSessionId());
      ClassCheckIn classCheckIn = classCheckInValidation.validateClassCheckInById(classCheckInId);
      CheckInStatus currentStatus = classCheckIn.getStatus();

      classCheckIn = ClassCheckInMapper.toEntity(requestDTO, classCheckIn.getUser(), classCheckIn.getClassSession(),
            classCheckIn);

      if (currentStatus != CheckInStatus.PRESENT && requestDTO.getStatus() == CheckInStatus.PRESENT) {
         classCheckIn.setCheckInTime(LocalDateTime.now());
         sendClassCheckInEvents(classCheckIn.getUser());
      }

      classCheckIn = classCheckInRepository.save(classCheckIn);

      return sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn));
   }

   @Transactional(readOnly = true)
   public List<ClassCheckInResponseDTO> getAllByUserId(UUID userId) {
      List<ClassCheckIn> classCheckIns = classCheckInRepository.findByUserId(userId);
      return classCheckIns.stream()
            .map(classCheckIn -> sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn)))
            .toList();
   }

   @Transactional(readOnly = true)
   public List<ClassCheckInResponseDTO> getAllByClassSessionId(UUID classSessionId) {
      List<ClassCheckIn> classCheckIns = classCheckInRepository.findByClassSessionId(classSessionId);
      return classCheckIns.stream()
            .map(classCheckIn -> sensitiveCheckInDataDecryptor.decrypt(ClassCheckInMapper.toResponse(classCheckIn)))
            .toList();
   }

   private void sendClassCheckInEvents(User user) {
      int streak = 0;

      List<ClassCheckIn> latestCheckIns = classCheckInRepository.findLatestCheckInsByUser(user.getId(),
            STREAK_DAYS_RANGE);

      for (ClassCheckIn checkIn : latestCheckIns) {
         if (checkIn.getStatus() == CheckInStatus.PRESENT) {
            streak += 1;
         } else {
            break;
         }
      }

      GamificationEventRequestDTO dto = GamificationEventRequestDTO.builder()
            .eventType("check-in")
            .userId(user.getId())
            .details(Map.of("firstCheckInToday", true, "streak", streak))
            .build();
      GamificationEvent event = new GamificationEvent(dto);
      publisher.publishEvent(event);
   }
}

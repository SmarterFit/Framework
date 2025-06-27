package com.framework.modules.checkin.validation;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.framework.common.exceptions.BusinessException;
import com.framework.modules.checkin.entity.GymCheckIn;
import com.framework.modules.checkin.repository.GymCheckInRepository;

@Component
public class GymCheckInValidation {
   private final GymCheckInRepository gymCheckInRepository;

   public GymCheckInValidation(GymCheckInRepository gymCheckInRepository) {
      this.gymCheckInRepository = gymCheckInRepository;
   }

   public GymCheckIn validateOpenGymCheckInByUserId(UUID userId) {
      return gymCheckInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId)
            .orElseThrow(() -> new BusinessException("Gym check-in not found"));
   }

   public void validateOpenCheckInNotExists(UUID userId) {
      if (gymCheckInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId).isPresent()) {
         throw new BusinessException("User already has a check-in");
      }
   }

   public void validateIsCommercialTime() {
      LocalDateTime now = LocalDateTime.now();
      int hour = now.getHour();
      DayOfWeek dayOfWeek = now.getDayOfWeek();

      if (hour < 6 || hour > 21 || dayOfWeek == DayOfWeek.SUNDAY) {
         throw new BusinessException("Gym is closed");
      }

   }
}

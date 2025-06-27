package com.framework.modules.checkin.validation;

import org.springframework.stereotype.Component;

import com.framework.common.exceptions.BusinessException;
import com.framework.modules.checkin.entity.ClassCheckIn;
import com.framework.modules.checkin.entity.id.ClassCheckInId;
import com.framework.modules.checkin.repository.ClassCheckInRepository;

@Component
public class ClassCheckInValidation {
   private final ClassCheckInRepository classCheckInRepository;

   public ClassCheckInValidation(ClassCheckInRepository classCheckInRepository) {
      this.classCheckInRepository = classCheckInRepository;
   }

   public ClassCheckIn validateClassCheckInById(ClassCheckInId classCheckInId) {
      return classCheckInRepository.findById(classCheckInId)
            .orElseThrow(() -> new BusinessException("Class check-in not found"));
   }

   public void validateClassCheckInNotExists(ClassCheckInId classCheckInId) {
      if (classCheckInRepository.findById(classCheckInId).isPresent()) {
         throw new BusinessException("User already has a check-in");
      }
   }
}

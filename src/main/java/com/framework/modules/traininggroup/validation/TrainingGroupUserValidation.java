package com.framework.modules.traininggroup.validation;

import org.springframework.stereotype.Component;

import com.framework.common.exceptions.BusinessException;
import com.framework.modules.traininggroup.entity.TrainingGroup;
import com.framework.modules.traininggroup.entity.TrainingGroupUser;
import com.framework.modules.traininggroup.entity.id.TrainingGroupUserId;
import com.framework.modules.traininggroup.repository.TrainingGroupUserRepository;
import com.framework.modules.useraccess.entity.User;

@Component
public class TrainingGroupUserValidation {
   private final TrainingGroupUserRepository trainingGroupUserRepository;

   public TrainingGroupUserValidation(TrainingGroupUserRepository trainingGroupUserRepository) {
      this.trainingGroupUserRepository = trainingGroupUserRepository;
   }

   public TrainingGroupUser validateTrainingGroupUserById(TrainingGroupUserId trainingGroupUserId) {
      return trainingGroupUserRepository.findById(trainingGroupUserId).orElseThrow(
            () -> new BusinessException("TrainingGroupUser not found."));
   }

   public void validateUserNotInTrainingGroup(TrainingGroup trainingGroup, User user) {
      boolean participantExists = trainingGroupUserRepository.existsByTrainingGroupIdAndUserId(trainingGroup.getId(),
            user.getId());

      if (participantExists) {
         throw new BusinessException("User already in the group");
      }
   }

   public void validateAtLeastOneAdmin(TrainingGroup trainingGroup) {
      boolean hasAdmin = trainingGroupUserRepository.existsByTrainingGroupIdAndIsAdmin(trainingGroup.getId(), true);

      if (!hasAdmin) {
         throw new BusinessException("The training group must have at least one admin.");
      }
   }

   public void validateTrainingGroupUserByIsAdmin(TrainingGroupUser trainingGroupUser, Boolean isAdmin) {
      if (trainingGroupUser.getIsAdmin() != isAdmin) {
         String message = "User already is " + (isAdmin ? "" : "not ") + "admin.";
         throw new BusinessException(message);
      }
   }
}

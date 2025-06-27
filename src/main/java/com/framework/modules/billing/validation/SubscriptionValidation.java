package com.framework.modules.billing.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.framework.common.enums.SubscriptionStatus;
import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.billing.entity.Subscription;
import com.framework.modules.billing.repository.SubscriptionRepository;

@Component
public class SubscriptionValidation {
   private final SubscriptionRepository subscriptionRepository;

   public SubscriptionValidation(SubscriptionRepository subscriptionRepository) {
      this.subscriptionRepository = subscriptionRepository;
   }

   public Subscription validateSubscriptionById(UUID id) {
      return subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
   }

   public void validateSubscriptionNotIsCanceled(Subscription subscription) {
      if (subscription.getStatus() == SubscriptionStatus.CANCELED) {
         throw new BusinessException("Subscription is canceled.");
      }
   }

   public void validateHasCurrentSubscription(UUID participantId) {
      if (!subscriptionRepository.existsCurrentSubscriptionByParticipantId(participantId)) {
         throw new BusinessException("User does not have an active subscription.");
      }
   }
}

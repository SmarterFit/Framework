package com.framework.modules.billing.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.framework.common.enums.SubscriptionStatus;
import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.billing.entity.Plan;
import com.framework.modules.billing.entity.Subscription;
import com.framework.modules.billing.repository.PlanRepository;

@Component
public class PlanValidation {

   private final PlanRepository planRepository;

   public PlanValidation(PlanRepository planRepository) {
      this.planRepository = planRepository;
   }

   public Plan validatePlanById(UUID id) {
      return planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
   }

   public void validateNoActiveSubscriptions(Plan plan) {
      for (Subscription subscription : plan.getSubscriptions()) {
         if (subscription.getStatus() == SubscriptionStatus.ACTIVE) {
            throw new BusinessException("Exists active subscriptions for this plan.");
         }
      }
   }

   public void validatePlanNotDeleted(Plan plan) {
      if (plan.getDeletedAt() != null) {
         throw new BusinessException("Plan already deleted.");
      }
   }
}

package com.framework.modules.classgroup.validation;

import com.framework.common.exceptions.BusinessException;
import com.framework.common.exceptions.ResourceAlreadyExistsException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.classgroup.entity.ClassGroupPlan;
import com.framework.modules.classgroup.repository.ClassGroupPlanRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClassGroupPlanValidation {

    public final ClassGroupPlanRepository classGroupPlanRepository;

    public ClassGroupPlanValidation(ClassGroupPlanRepository classGroupPlanRepository) {
        this.classGroupPlanRepository = classGroupPlanRepository;
    }

    public void validateClassGroupPlanExists(UUID planId, UUID classGroupId) {
        if (classGroupPlanRepository.existsByPlanIdAndClassGroupId(planId, classGroupId)) {
            throw new ResourceAlreadyExistsException("Plan already exists for this class group.");
        }
    }

    public void validateClassGroupPlanNotExists(UUID planId, UUID classGroupId) {
        if (!classGroupPlanRepository.existsByPlanIdAndClassGroupId(planId, classGroupId)) {
            throw new ResourceAlreadyExistsException("Plan not exists for this class group.");
        }
    }

    public ClassGroupPlan validateClassGroupPlanById(UUID planId, UUID classGroupId) {
        return classGroupPlanRepository.findByPlanIdAndClassGroupId(planId, classGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Class group plan not found."));

    }

    public void validateClassGroupPlanAndSubscription(UUID classGroupId, UUID subscriptionPlanId) {
        List<ClassGroupPlan> classGroupPlans = classGroupPlanRepository.findAllByClassGroupId(classGroupId);
        boolean planExistsInGroup = classGroupPlans.stream()
                .anyMatch(cgp -> cgp.getPlan().getId().equals(subscriptionPlanId));

        if (!planExistsInGroup) {
            throw new BusinessException("Subscription plan is not associated with this class group.");
        }
    }


}
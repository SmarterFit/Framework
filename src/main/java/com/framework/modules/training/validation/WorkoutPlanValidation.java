package com.framework.modules.training.validation;

import com.framework.common.exceptions.ResourceAlreadyExistsException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.training.entity.WorkoutPlan;
import com.framework.modules.training.repository.WorkoutPlanRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkoutPlanValidation {

    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanValidation(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    public WorkoutPlan validateWorkoutPlanById(UUID id) {
        return workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan not found"));
    }

    public void existsWorkoutPlanById(UUID id) {
        if (workoutPlanRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("WorkoutPlan already exists");
        }
    }

    public boolean noExistsWorkoutPlanById(UUID id) {
        return !workoutPlanRepository.existsById(id);
    }

}

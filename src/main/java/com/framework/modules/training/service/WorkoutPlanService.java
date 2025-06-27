package com.framework.modules.training.service;

import com.framework.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.framework.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.framework.modules.training.entity.TrainingGoal;
import com.framework.modules.training.entity.WorkoutPlan;
import com.framework.modules.training.mapper.WorkoutPlanMapper;
import com.framework.modules.training.repository.WorkoutPlanRepository;
import com.framework.modules.training.validation.TrainingGoalValidation;
import com.framework.modules.training.validation.WorkoutPlanValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanValidation workoutPlanValidation;
    private final TrainingGoalValidation trainingGoalValidation;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository,
            WorkoutPlanValidation workoutPlanValidation,
            TrainingGoalValidation trainingGoalValidation) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutPlanValidation = workoutPlanValidation;
        this.trainingGoalValidation = trainingGoalValidation;
    }

    @Transactional
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO dto) {
        workoutPlanValidation.existsWorkoutPlanById(dto.getTrainingGoalId());
        TrainingGoal trainingGoal = trainingGoalValidation.validateTrainingGoalById(dto.getTrainingGoalId());

        WorkoutPlan plan = WorkoutPlanMapper.toEntity(dto, trainingGoal);

        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return WorkoutPlanMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanByUserId(UUID userId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);
        return WorkoutPlanMapper.toResponse(plan);
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanById(UUID id) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(id);
        return WorkoutPlanMapper.toResponse(plan);
    }

    @Transactional
    public WorkoutPlanResponseDTO updateWorkoutPlan(WorkoutPlanRequestDTO dto) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(dto.getTrainingGoalId());

        WorkoutPlan updated = workoutPlanRepository.save(WorkoutPlanMapper.toEntity(dto, plan));
        return WorkoutPlanMapper.toResponse(updated);
    }

    @Transactional
    public void deleteWorkoutPlan(UUID userId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);
        workoutPlanRepository.delete(plan);
    }
}
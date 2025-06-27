package com.framework.modules.training.repository;

import com.framework.modules.training.entity.WorkoutPlan;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {
}

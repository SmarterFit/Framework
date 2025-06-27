package com.framework.modules.training.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.training.dto.request.TrainingGoalRequestDTO;
import com.framework.modules.training.dto.response.TrainingGoalResponseDTO;
import com.framework.modules.training.entity.TrainingGoal;
import com.framework.modules.useraccess.entity.User;

public class TrainingGoalMapper {

    private TrainingGoalMapper() {
        // Private constructor to prevent instantiation
    }

    public static TrainingGoal toEntity(TrainingGoalRequestDTO dto, User user) {
        return toEntity(dto, new TrainingGoal(), user);
    }

    public static TrainingGoal toEntity(TrainingGoalRequestDTO dto, TrainingGoal trainingGoal, User user) {
        if (trainingGoal == null) {
            throw new ResourceNotFoundException("TrainingGoal not found");
        }

        trainingGoal.setUser(user);
        trainingGoal = GenericMapper.map(dto, trainingGoal);

        return trainingGoal;
    }

    public static TrainingGoal toEntity(TrainingGoalRequestDTO dto, TrainingGoal trainingGoal) {
        if (trainingGoal == null) {
            throw new ResourceNotFoundException("TrainingGoal not found");
        }
        trainingGoal = GenericMapper.map(dto, trainingGoal);

        return trainingGoal;
    }

    public static TrainingGoalResponseDTO toResponse(TrainingGoal trainingGoal) {
        if (trainingGoal == null) {
            throw new ResourceNotFoundException("TrainingGoal not found");
        }

        return GenericMapper.map(trainingGoal, TrainingGoalResponseDTO.class);
    }
}

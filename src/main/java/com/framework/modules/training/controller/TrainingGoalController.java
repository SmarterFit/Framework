package com.framework.modules.training.controller;

import com.framework.modules.training.dto.request.TrainingGoalRequestDTO;
import com.framework.modules.training.dto.response.TrainingGoalResponseDTO;
import com.framework.modules.training.service.TrainingGoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("treinos/objetivos")
public class TrainingGoalController {

    private final TrainingGoalService trainingGoalService;

    public TrainingGoalController(TrainingGoalService trainingGoalService) {
        this.trainingGoalService = trainingGoalService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TrainingGoalResponseDTO> create(@Valid @RequestBody TrainingGoalRequestDTO requestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.createTrainingGoal(requestDTO, requesterId);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<TrainingGoalResponseDTO> getByUserId(@RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.getTrainingGoalByUserId(requesterId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<TrainingGoalResponseDTO> update(@Valid @RequestBody TrainingGoalRequestDTO requestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.updateTrainingGoal(requesterId, requestDTO);
        return ResponseEntity.ok(response);
    }
}

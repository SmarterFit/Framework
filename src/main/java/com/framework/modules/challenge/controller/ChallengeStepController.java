package com.framework.modules.challenge.controller;

import com.framework.modules.challenge.dto.request.ChallengeStepRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeStepResponseDTO;
import com.framework.modules.challenge.service.ChallengeStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("desafio/steps")
@CrossOrigin
public class ChallengeStepController {

    private final ChallengeStepService challengeStepService;

    public ChallengeStepController(ChallengeStepService service) {
        this.challengeStepService = service;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ChallengeStepResponseDTO> create(@RequestBody ChallengeStepRequestDTO request) {
        return ResponseEntity.status(201).body(challengeStepService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeStepResponseDTO> update(@PathVariable UUID id,
            @RequestBody ChallengeStepRequestDTO request) {
        return ResponseEntity.ok(challengeStepService.update(id, request));
    }

    @PatchMapping("/toggle/{id}")
    public ResponseEntity<ChallengeStepResponseDTO> toggle(@PathVariable UUID id) {
        return ResponseEntity.ok(challengeStepService.toggle(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        challengeStepService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<ChallengeStepResponseDTO>> findByDay(@PathVariable UUID dayId) {
        return ResponseEntity.ok(challengeStepService.findByDayId(dayId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeStepResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(challengeStepService.findById(id));
    }
}

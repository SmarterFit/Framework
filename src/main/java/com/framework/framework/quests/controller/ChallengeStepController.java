package com.framework.framework.quests.controller;

import com.framework.framework.quests.dto.request.ChallengeStepRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeStepResponseDTO;
import com.framework.framework.quests.service.ChallengeStepService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/steps")
@CrossOrigin
public class ChallengeStepController {

    private final ChallengeStepService service;

    public ChallengeStepController(ChallengeStepService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChallengeStepResponseDTO> create(@RequestBody ChallengeStepRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeStepResponseDTO> update(@PathVariable UUID id,
                                                           @RequestBody ChallengeStepRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<ChallengeStepResponseDTO>> findByDay(@PathVariable UUID dayId) {
        return ResponseEntity.ok(service.findByDayId(dayId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeStepResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}

package com.framework.modules.challenge.controller;

import com.framework.modules.challenge.dto.request.ChallengeTrailRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeTrailResponseDTO;
import com.framework.modules.challenge.service.ChallengeTrailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("desafio/trilha")
@CrossOrigin
public class ChallengeTrailController {

    private final ChallengeTrailService service;

    public ChallengeTrailController(ChallengeTrailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChallengeTrailResponseDTO> create(@RequestBody ChallengeTrailRequestDTO request,
            @RequestHeader("X-User-Id") UUID requesterId) {
        return ResponseEntity.status(201).body(service.create(request, requesterId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeTrailResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/quest/{questId}")
    public ResponseEntity<ChallengeTrailResponseDTO> getByQuestId(@PathVariable UUID questId) {
        return ResponseEntity.ok(service.getByQuestId(questId));
    }
}

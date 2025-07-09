package com.framework.framework.quests.controller;

import com.framework.framework.quests.dto.request.ChallengeTrailRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeTrailResponseDTO;
import com.framework.framework.quests.service.ChallengeTrailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trails")
@CrossOrigin
public class ChallengeTrailController {

    private final ChallengeTrailService service;

    public ChallengeTrailController(ChallengeTrailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChallengeTrailResponseDTO> create(@RequestBody ChallengeTrailRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeTrailResponseDTO> update(@PathVariable UUID id,
                                                            @RequestBody ChallengeTrailRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quest/{questId}")
    public ResponseEntity<List<ChallengeTrailResponseDTO>> findByQuest(@PathVariable UUID questId) {
        return ResponseEntity.ok(service.findByQuestId(questId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeTrailResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}

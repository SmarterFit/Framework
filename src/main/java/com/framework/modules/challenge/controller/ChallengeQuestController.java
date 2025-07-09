package com.framework.modules.challenge.controller;

import com.framework.modules.challenge.dto.request.ChallengeQuestRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeQuestResponseDTO;
import com.framework.modules.challenge.service.ChallengeQuestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/desafio/form")
@CrossOrigin
public class ChallengeQuestController {

    private final ChallengeQuestService challengeQuestService;

    public ChallengeQuestController(ChallengeQuestService challengeQuestService) {
        this.challengeQuestService = challengeQuestService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ChallengeQuestResponseDTO> createChallengeQuest(
            @RequestBody @Valid ChallengeQuestRequestDTO requestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {

        ChallengeQuestResponseDTO responseDTO = challengeQuestService.createChallengeQuest(requestDTO, requesterId);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeQuestResponseDTO> getChallengeQuestById(@PathVariable UUID id) {
        return ResponseEntity.ok(challengeQuestService.getChallengeQuestById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeQuestResponseDTO> updateChallengeQuest(
            @PathVariable UUID id,
            @RequestBody @Valid ChallengeQuestRequestDTO requestDTO) {

        ChallengeQuestResponseDTO responseDTO = challengeQuestService.updateChallengeQuest(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChallengeQuest(@PathVariable UUID id) {
        challengeQuestService.deleteChallengeQuest(id);
        return ResponseEntity.noContent().build();
    }
}
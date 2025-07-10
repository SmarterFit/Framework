package com.framework.framework.challenge.controller;

import com.framework.framework.challenge.service.ChallengeService;
import com.framework.modules.challenge.dto.response.ChallengeTrailResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/desafio/trilha/ia")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }


    @PostMapping("/{challengeQuestId}")
    public ResponseEntity<ChallengeTrailResponseDTO> generateChallenge(
            @PathVariable("challengeQuestId") UUID challengeQuestId,
            @RequestHeader("X-User-Id") UUID requesterId) throws IOException {

        ChallengeTrailResponseDTO response = challengeService.generateChallenge(requesterId, challengeQuestId);
        return ResponseEntity.status(201).body(response);
    }

}

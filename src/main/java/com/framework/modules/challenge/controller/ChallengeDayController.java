package com.framework.modules.challenge.controller;

import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestCreateDTO;
import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestUpdateDTO;
import com.framework.modules.challenge.dto.response.ChallengeDayResponseDTO;
import com.framework.modules.challenge.service.ChallengeDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("desafio/dia")
@CrossOrigin
public class ChallengeDayController {

    private final ChallengeDayService challengeDayService;

    public ChallengeDayController(ChallengeDayService service) {
        this.challengeDayService = service;
    }

    @PostMapping
    public ResponseEntity<ChallengeDayResponseDTO> create(@RequestBody ChallengeDayRequestCreateDTO request) {
        return ResponseEntity.status(201).body(challengeDayService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDayResponseDTO> update(@PathVariable UUID id,
                                                          @RequestBody ChallengeDayRequestUpdateDTO request) {
        return ResponseEntity.ok(challengeDayService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        challengeDayService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<ChallengeDayResponseDTO>> findByTrail(@PathVariable UUID trailId) {
        return ResponseEntity.ok(challengeDayService.findByTrailId(trailId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDayResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(challengeDayService.findById(id));
    }
}
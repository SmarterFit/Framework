package com.framework.framework.quests.controller;

import com.framework.framework.quests.dto.request.ChallengeDayRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeDayResponseDTO;
import com.framework.framework.quests.service.ChallengeDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/days")
@CrossOrigin
public class ChallengeDayController {

    private final ChallengeDayService service;

    public ChallengeDayController(ChallengeDayService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChallengeDayResponseDTO> create(@RequestBody ChallengeDayRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeDayResponseDTO> update(@PathVariable UUID id,
                                                          @RequestBody ChallengeDayRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<ChallengeDayResponseDTO>> findByTrail(@PathVariable UUID trailId) {
        return ResponseEntity.ok(service.findByTrailId(trailId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDayResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
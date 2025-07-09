package com.framework.framework.quests.controller;

import com.framework.framework.quests.dto.request.PersonalQuestRequestDTO;
import com.framework.framework.quests.dto.response.PersonalQuestResponseDTO;
import com.framework.framework.quests.service.PersonalQuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quests")
@CrossOrigin
public class PersonalQuestController {

    private final PersonalQuestService service;

    public PersonalQuestController(PersonalQuestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PersonalQuestResponseDTO> create(@RequestBody PersonalQuestRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalQuestResponseDTO> update(@PathVariable UUID id,
                                                           @RequestBody PersonalQuestRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PersonalQuestResponseDTO>> findByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(service.findAllByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalQuestResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }
}

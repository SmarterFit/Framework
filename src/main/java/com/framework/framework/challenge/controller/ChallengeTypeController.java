package com.framework.framework.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framework.framework.challenge.dto.response.ChallengeTypeResponseDTO;
import com.framework.framework.challenge.service.ChallengeTypeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/desafio/trilha/tipo")
public class ChallengeTypeController {
   private final ChallengeTypeService challengeTypeService;

   @Autowired
   public ChallengeTypeController(ChallengeTypeService challengeTypeService) {
      this.challengeTypeService = challengeTypeService;
   }

   @GetMapping
   public ResponseEntity<List<ChallengeTypeResponseDTO>> getEnabledChallengeTypes() {
      return ResponseEntity.ok(challengeTypeService.getEnabledChallengeTypes());
   }
}

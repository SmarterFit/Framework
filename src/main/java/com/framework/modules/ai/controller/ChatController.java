package com.framework.modules.ai.controller;

import com.framework.modules.ai.generator.WorkoutPlanAIGenerator;
import com.framework.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.framework.modules.ai.service.ChatService;

import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {
   private final ChatService chatService;
   private final WorkoutPlanAIGenerator workoutPlanAIGenerator;

   @Autowired
   public ChatController(ChatService chatService, WorkoutPlanAIGenerator workoutPlanAIGenerator) {
      this.chatService = chatService;
        this.workoutPlanAIGenerator = workoutPlanAIGenerator;
   }

   @PostMapping("/ask")
   public Flux<String> askGroq(@RequestBody String prompt,
                              @RequestHeader("X-User-Id") UUID requesterId) {
      return chatService.askGroq(prompt , requesterId);
   }

   @PostMapping("/ask/training")
   public WorkoutPlanResponseDTO askGroqTraining(@RequestHeader("X-User-Id") UUID requesterId) {
      return workoutPlanAIGenerator.generatePlan(requesterId);
   }
}

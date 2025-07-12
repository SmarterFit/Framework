package com.framework.framework.challenge.service;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.framework.challenge.dto.response.ChallengeTypeResponseDTO;
import com.framework.framework.challenge.entity.ChallengeType;
import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.challenge.mapper.ChallengeTypeMapper;
import com.framework.framework.challenge.registry.ChallengeHandlerRegistry;
import com.framework.framework.challenge.repository.ChallengeTypeRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ChallengeTypeService {
   private final ChallengeTypeRepository challengeTypeRepository;
   private final ChallengeHandlerRegistry challengeHandlerRegistry;

   @Autowired
   public ChallengeTypeService(ChallengeTypeRepository challengeTypeRepository,
         ChallengeHandlerRegistry challengeHandlerRegistry) {
      this.challengeTypeRepository = challengeTypeRepository;
      this.challengeHandlerRegistry = challengeHandlerRegistry;
   }

   @PostConstruct
   @Transactional
   public void init() {
      for (Entry<String, ChallengeHandler> entry : challengeHandlerRegistry.getActiveHandlers().entrySet()) {
         String typeId = entry.getKey();
         ChallengeHandler handler = entry.getValue();

         ChallengeType type = challengeTypeRepository.findByName(entry.getKey()).orElseGet(ChallengeType::new);
         type.setId(typeId);
         type.setName(handler.getChallengeTypeName());
         type.setEnabled(true);
         type.setHandlerClass(handler.getClass().getName());
         challengeTypeRepository.save(type);
      }

      challengeTypeRepository.deactivateTypesNotIn(challengeHandlerRegistry.getSupportedMetricTypes());
   }

   @Transactional(readOnly = true)
   public List<ChallengeTypeResponseDTO> getEnabledChallengeTypes() {
      return challengeTypeRepository.findAllByEnabledTrue()
            .stream()
            .map(ChallengeTypeMapper::toResponse)
            .toList();
   }
}

package com.framework.framework.challenge.handle.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.GenericMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.service.UserMetricService;

@Component
public class GenericChallengeTrailHandler extends ChallengeHandler {
   private static final String DEFAULT_GENERIC_VALUE = "0.0";

   protected GenericChallengeTrailHandler(@Qualifier("challengeChatClient") ChatClient chatClient,
         UserMetricService userMetricService) {
      super(chatClient, userMetricService);
   }

   @Override
   protected String fetchUserMetric(MetricType metricType, UUID userId, MetricDataDTO metricDataDTO) {
      AbstractMetricRecord lastMetric = userMetricService.getLastMetric(userId, metricType.getId()).orElse(null);

      return Optional.ofNullable(lastMetric)
            .filter(metric -> metric instanceof GenericMetricRecord)
            .map(metric -> (GenericMetricRecord) metric)
            .map(GenericMetricRecord::getValue)
            .map(String::valueOf)
            .orElse(DEFAULT_GENERIC_VALUE);
   }

   @Override
   public String getChallengeTypeId() {
      return "CHALLENGE_GENERIC";
   }

   @Override
   public String getChallengeTypeName() {
      return "Desafio Geral";
   }
}
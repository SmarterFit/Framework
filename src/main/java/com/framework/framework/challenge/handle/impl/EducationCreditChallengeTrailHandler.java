package com.framework.framework.challenge.handle.impl;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.repository.EducationCreditRecordRepository;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.service.UserMetricService;

@Component
public class EducationCreditChallengeTrailHandler extends ChallengeHandler {
   private final EducationCreditRecordRepository educationCreditRecordRepository;

   public EducationCreditChallengeTrailHandler(EducationCreditRecordRepository educationCreditRecordRepository,
         @Qualifier("challengeChatClient") ChatClient chatClient,
         UserMetricService userMetricService) {
      super(chatClient, userMetricService);
      this.educationCreditRecordRepository = educationCreditRecordRepository;
   }

   @Override
   protected String fetchUserMetric(MetricType metricType, UUID userId, MetricDataDTO metricDataDTO) {
      MetricValidationChain chain = new MetricValidationChain(
            Arrays.asList(new RequiredFieldValidation("keyword")));

      metricValidationContext = chain.execute(metricDataDTO, metricType);
      String keyword = metricValidationContext.getNormalized(getAdditionalContext(), String.class);

      double hours = educationCreditRecordRepository.sumHoursByProfileIdAndKeyword(userId, keyword);
      return String.valueOf(hours);
   }

   @Override
   public String getAdditionalContext() {
      String keyword = metricValidationContext.getNormalized("keyword", String.class);
      return "O desafio de nota envolve a palavra chave '" + keyword + "': "
            + " onde o número de horas fornecido é a soma total de horas de"
            + " educação desse usuário que contenham essa palavra.";
   }

   @Override
   public String getChallengeTypeId() {
      return "CHALLENGE_EDUCATION_CREDIT";
   }

   @Override
   public String getChallengeTypeName() {
      return "Desafio de Credito de Educação";
   }

}

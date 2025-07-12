package com.framework.framework.challenge.handle.impl;

import com.framework.framework.challenge.handle.ChallengeHandler;
import com.framework.framework.usermetric.entity.grade.ClassGradeMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.repository.ClassGradeMetricRecordRepository;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.ClassGroupIdValidation;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.validation.ClassGroupValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class GradeChallengeTrailHandler extends ChallengeHandler {

   private final ClassGroupValidation classGroupValidation;
   private final ClassGradeMetricRecordRepository classGradeMetricRecordRepository;

   protected GradeChallengeTrailHandler(ChatClient chatClient, UserMetricService userMetricService,
         ClassGroupValidation classGroupValidation,
         ClassGradeMetricRecordRepository classGradeMetricRecordRepository) {
      super(chatClient, userMetricService);
      this.classGroupValidation = classGroupValidation;
      this.classGradeMetricRecordRepository = classGradeMetricRecordRepository;
   }

   @Override
   protected String fetchUserMetric(MetricType metricType, UUID userId, MetricDataDTO metricDataDTO) {

      MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
            new RequiredFieldValidation("classGroupId"),
            new ClassGroupIdValidation("classGroupId", classGroupValidation)));

      MetricValidationContext context = chain.execute(metricDataDTO, metricType);
      UUID classGroupId = context.getNormalized("classGroupId", UUID.class);

      ClassGradeMetricRecord record = classGradeMetricRecordRepository
            .findFirstByProfileIdAndMetricTypeIdAndClassGroupIdOrderByCreatedAtDesc(userId, metricType.getId(),
                  classGroupId)
            .orElseGet(() -> {
               ClassGroup classGroup = classGroupValidation.validateClassGroupById(classGroupId);
               ClassGradeMetricRecord emptyRecord = new ClassGradeMetricRecord();
               emptyRecord.setGrade(0.0);
               emptyRecord.setClassGroup(classGroup);
               return emptyRecord;
            });
      return String.valueOf(record.getGrade());
   }

   @Override
   public String getChallengeTypeId() {
      return "CHALLENGE_GRADE";
   }

   @Override
   public String getChallengeTypeName() {
      return "Desafio de Nota";
   }
}
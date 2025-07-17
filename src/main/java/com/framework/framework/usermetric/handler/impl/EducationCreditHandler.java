package com.framework.framework.usermetric.handler.impl;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.framework.usermetric.entity.educationcredit.EducationCreditRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.handler.AbstractMetricHandler;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.NumericRangeValidation;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class EducationCreditHandler extends AbstractMetricHandler {
    private final ApplicationEventPublisher publisher;

    public EducationCreditHandler(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public MetricValidationContext validate(MetricDataDTO request, MetricType metricType) {
        MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
                new RequiredFieldValidation("userId"),
                new RequiredFieldValidation("courseName"),
                new RequiredFieldValidation("completionDate"),
                new RequiredFieldValidation("hours"),
                new NumericRangeValidation("hours"),
                new RequiredFieldValidation("institution")));
        return chain.execute(request, metricType);
    }

    @Override
    public List<String> analyze(MetricValidationContext context) {
        List<String> alerts = new ArrayList<>();
        double horas = context.getNormalized("hours", Double.class);

        if (horas > 120) {
            alerts.add("Parabéns, você estuda muito!");
        }

        return alerts;
    }

    @Override
    public AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source) {
        EducationCreditRecord record = new EducationCreditRecord();
        double hours = context.getNormalized("hours", Double.class);
        MetricDataDTO request = context.getOriginalRequest();
        String courseName = request.getData().get("courseName").toString();
        String institution = request.getData().get("institution").toString();
        LocalDate completionDate = LocalDate.parse(request.getData().get("completionDate").toString());

        record.setCourseName(courseName);
        record.setCompletionDate(completionDate);
        record.setHours(hours);
        record.setInstitution(institution);

        record.setProfile(profile);
        record.setSource(source);
        record.setMetricType(context.getMetricType());
        return record;
    }

    @Override
    public MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record) {
        EducationCreditRecord creditRecord = (EducationCreditRecord) record;
        MetricDataResponseDTO responseDTO = new MetricDataResponseDTO();

        responseDTO.setId(creditRecord.getId());
        responseDTO.setMetricType(creditRecord.getMetricType().getType());
        responseDTO.setCreatedAt(creditRecord.getCreatedAt());
        responseDTO.setData(creditRecord.getDetails());
        return responseDTO;
    }

    @Override
    public String getSupportedType() {
        return "Créditos de Educação";
    }

    @Override
    public String getUnit() {
        return "hours";
    }

    @Override
    public double getMinThreshold() {
        return 0.0;
    }

    @Override
    public double getMaxThreshold() {
        return 1000.0;
    }

    @Override
    public void afterValidation(MetricValidationContext context) {
        String userIdStr = (String) context.getOriginalRequest().getData().get("userId");
        UUID userId = UUID.fromString(userIdStr);
        Double hours = context.getNormalized("hours", Double.class);

        GamificationEventRequestDTO dto = GamificationEventRequestDTO.builder()
                .eventType("EDUCATION_CREDITS")
                .userId(userId)
                .details(Map.of("hours", hours))
                .build();

        GamificationEvent event = new GamificationEvent(dto);
        publisher.publishEvent(event);
    }

    @Override
    public boolean supports(String metricType) {
        return "EDUCATION_CREDIT".equalsIgnoreCase(metricType);
    }
}

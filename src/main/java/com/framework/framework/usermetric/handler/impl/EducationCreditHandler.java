package com.framework.framework.usermetric.handler.impl;

import com.framework.framework.usermetric.dto.EducationCreditDTO;
import com.framework.framework.usermetric.entity.educationcredit.EducationCreditRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.entity.MetricProcessResult;
import com.framework.framework.usermetric.handler.AbstractMetricHandler;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.MetricValidationChain;
import com.framework.framework.usermetric.validation.chain.NumericRangeValidation;
import com.framework.framework.usermetric.validation.chain.RequiredFieldValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.framework.usermetric.repository.EducationCreditRecordRepository;
import com.framework.modules.metric.repository.MetricTypeRepository;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class EducationCreditHandler extends AbstractMetricHandler {

    private final EducationCreditRecordRepository educationCreditRecordRepository;

    public EducationCreditHandler(EducationCreditRecordRepository educationCreditRecordRepository) {
        this.educationCreditRecordRepository = educationCreditRecordRepository;
    }

    @Override
    public MetricValidationContext validate(MetricDataDTO request, MetricType metricType) {
        MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
        new RequiredFieldValidation("courseName"),
        new RequiredFieldValidation("completionDate"),
        new RequiredFieldValidation("hours"),
        new RequiredFieldValidation("institution")
    ));
    return chain.execute(request, metricType);
    }

    private EducationCreditDTO convert(MetricDataDTO request) {
        EducationCreditDTO dto = new EducationCreditDTO();
        dto.setCourseName((String) request.getData().get("courseName"));
        dto.setCompletionDate(LocalDate.parse((String) request.getData().get("completionDate")));
        dto.setHours(Double.parseDouble(request.getData().get("hours").toString()));
        dto.setInstitution((String) request.getData().get("institution"));
        return dto;
    }

    @Override
    public List<String> analyze(MetricValidationContext context) {
        List<String> alerts = new ArrayList<>();
        double horas = context.getNormalized("hours", Double.class);

        if (horas > 1000) {
            alerts.add("Carga horária muito alta, por favor verifique.");
        }

        return alerts;
    }

    @Override
    public AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source) {
        EducationCreditRecord record = new EducationCreditRecord();
        double hours = context.getNormalized("hours", Double.class);
        String courseName = context.getNormalized("courseName", String.class);
        String institution = context.getNormalized("institution", String.class);
        LocalDate completionDate = context.getNormalized("completionDate", LocalDate.class);
        

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
        responseDTO.setData(creditRecord.getDetails());
        return responseDTO;
    }

    @Override
    public String getSupportedType() {
        return "EDUCATION_CREDIT";
    }

    @Override
    public boolean supports(String metricType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
    }
}


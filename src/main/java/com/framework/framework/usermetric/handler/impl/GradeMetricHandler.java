package com.framework.framework.usermetric.handler.impl;

import com.framework.framework.usermetric.entity.grade.ClassGradeMetricRecord;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.handler.AbstractMetricHandler;
import com.framework.framework.usermetric.validation.MetricValidationContext;
import com.framework.framework.usermetric.validation.chain.*;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.validation.ClassGroupValidation;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.useraccess.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GradeMetricHandler extends AbstractMetricHandler {

    public List<String> alerts;
    private final ClassGroupValidation classGroupValidation;

    public GradeMetricHandler(ClassGroupValidation classGroupValidation) {
        this.classGroupValidation = classGroupValidation;
    }

    @Override
    protected MetricValidationContext validate(MetricDataDTO request, MetricType metricType) {
        MetricValidationChain chain = new MetricValidationChain(Arrays.asList(
                new RequiredFieldValidation("grade"),
                new RequiredFieldValidation("classGroupId"),
                new NumericRangeValidation("grade"),
                new ClassGroupIdValidation("classGroupId", classGroupValidation)
        ));

        return chain.execute(request, metricType);
    }

    @Override
    protected List<String> analyze(MetricValidationContext context) {
        List<String> alerts = new ArrayList<>();
        double grade = context.getNormalized("grade", Double.class);

        if (grade < 6) {
            alerts.add("Grade is below average.");
        }
        if( grade > 8) {
            alerts.add("Congratulations! You have an excellent grade.");
        }
        return alerts;
    }

    @Override
    protected AbstractMetricRecord build(MetricValidationContext context, Profile profile, String source) {
        ClassGradeMetricRecord record = new ClassGradeMetricRecord();

        double grade = context.getNormalized("grade", Double.class);
        ClassGroup classGroup = context.getNormalized("classGroup", ClassGroup.class);

        record.setGrade(grade);
        record.setClassGroup(classGroup);

        record.setMetricType(context.getMetricType());
        record.setSource(source);
        record.setProfile(profile);

        return record;
    }

    @Override
    public MetricDataResponseDTO toResponseDTO(AbstractMetricRecord record) {
        ClassGradeMetricRecord gradeRecord = (ClassGradeMetricRecord) record;

        Map<String, Object> data = Map.of(
                "Nota", gradeRecord.getGrade(),
                "Turma", gradeRecord.getClassGroup().getTitle(),
                "classGroupId", gradeRecord.getClassGroup().getId()
        );

        return new MetricDataResponseDTO(
                gradeRecord.getId(),
                gradeRecord.getMetricType().getType(),
                data,
                record.getCreatedAt()
        );
    }

    @Override
    public boolean supports(String metricType) {
        return getSupportedType().equalsIgnoreCase(metricType);

    }

    @Override
    public String getSupportedType() {
        return "Nota";
    }


}

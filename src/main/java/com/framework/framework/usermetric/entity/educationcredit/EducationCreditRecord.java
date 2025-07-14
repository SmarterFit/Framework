package com.framework.framework.usermetric.entity.educationcredit;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.time.LocalDate;

@Entity
@Table(name = "education_credit_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationCreditRecord extends AbstractMetricRecord {

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private LocalDate completionDate;

    @Column(nullable = false)
    private double hours;

    @Column(nullable = false)
    private String institution;

    @Override
    public double getValue() {
        return this.hours;
    }

    @Override
    public Map<String, Object> getDetails() {
        return Map.of(
            "courseName", courseName,
            "completionDate", completionDate,
            "hours", hours,
            "institution", institution
        );
    }
}


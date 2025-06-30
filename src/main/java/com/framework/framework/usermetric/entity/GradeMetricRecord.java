package com.framework.framework.usermetric.entity;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.classgroup.entity.ClassGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "GRADE_METRIC_RECORD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeMetricRecord extends AbstractMetricRecord {

    private double grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id")
    private ClassGroup classGroup;

    @Override
    public double getValue() {
        return this.grade;
    }

    @Override
    public Map<String, Object> getDetails() {
        return Map.of("grade", this.grade);
    }
}

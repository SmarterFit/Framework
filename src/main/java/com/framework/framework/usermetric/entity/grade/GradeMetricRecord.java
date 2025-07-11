package com.framework.framework.usermetric.entity.grade;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "grade_type")
@Getter
@Setter
public abstract class GradeMetricRecord extends AbstractMetricRecord {

    protected double grade;

    @Override
    public double getValue() {
        return this.grade;
    }

    @Override
    public Map<String, Object> getDetails() {
        return Map.of("grade", this.grade);
    }
}

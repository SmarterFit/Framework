package com.framework.framework.usermetric.entity;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "WEIGHT_METRIC_RECORD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightMetricRecord extends AbstractMetricRecord {

    private Double weight;

    private LocalDate measurementDate;

    @Override
    public double getValue() {
        return this.weight;
    }

    @Override
    public Map<String, Object> getDetails() {
        return Map.of("weight", this.weight);
    }
}

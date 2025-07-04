package com.framework.framework.usermetric.entity.generic;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "GENERIC_METRIC_RECORD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericMetricRecord extends AbstractMetricRecord {

    private double value;

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public Map<String, Object> getDetails() {
        return Map.of("value", this.value);
    }
}

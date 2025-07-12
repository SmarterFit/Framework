package com.framework.framework.usermetric.entity.grade;

import com.framework.modules.classgroup.entity.ClassGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Setter
@Getter
@DiscriminatorValue("CLASS")
public class ClassGradeMetricRecord extends GradeMetricRecord {

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "class_group_id", nullable = false)
   private ClassGroup classGroup;

   @Override
   public Map<String, Object> getDetails() {
      return Map.of(
            "grade", this.grade,
            "classGroupId", this.classGroup.getId(),
            "classGroupName", this.classGroup.getTitle()

      );
   }
}
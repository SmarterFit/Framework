package com.framework.modules.billing.event;

import org.springframework.context.ApplicationEvent;

import com.framework.modules.billing.entity.Plan;

import lombok.Getter;

@Getter
public class PlanDeletedEvent extends ApplicationEvent {
   private final Plan plan;

   public PlanDeletedEvent(Plan plan) {
      super(plan);
      this.plan = plan;
   }
}

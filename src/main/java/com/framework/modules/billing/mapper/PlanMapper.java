package com.framework.modules.billing.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.billing.dto.request.plan.CreatePlanRequestDTO;
import com.framework.modules.billing.dto.request.plan.UpdatePlanRequestDTO;
import com.framework.modules.billing.dto.response.plan.CreatedPlanResponseDTO;
import com.framework.modules.billing.entity.Plan;

public class PlanMapper {
   private PlanMapper() {
      // Private constructor to prevent instantiation
   }

   public static Plan toEntity(CreatePlanRequestDTO dto) {
      return toEntity(dto, new Plan());
   }

   public static Plan toEntity(CreatePlanRequestDTO dto, Plan plan) {
      if (plan == null) {
         throw new ResourceNotFoundException("Plan not found.");
      }

      plan = GenericMapper.map(dto, plan);

      return plan;
   }

   public static Plan toEntity(UpdatePlanRequestDTO dto, Plan plan) {
      if (plan == null) {
         throw new ResourceNotFoundException("Plan not found.");
      }

      plan = GenericMapper.map(dto, plan);

      return plan;
   }

   public static CreatedPlanResponseDTO toResponse(Plan plan) {
      if (plan == null) {
         throw new ResourceNotFoundException("Plan not found.");
      }

      return GenericMapper.map(plan, CreatedPlanResponseDTO.class);
   }
}

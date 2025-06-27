package com.framework.modules.classgroup.controller;

import com.framework.common.enums.RoleType;
import com.framework.common.security.RequireRole;
import com.framework.modules.billing.dto.response.plan.PlanResponseDTO;
import com.framework.modules.classgroup.dto.request.classgroupplan.CreateClassGroupPlanDTO;
import com.framework.modules.classgroup.service.ClassGroupPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/turma")
public class ClassGroupPlanController {
    public final ClassGroupPlanService classGroupPlanService;

    public ClassGroupPlanController(ClassGroupPlanService classGroupPlanService) {
        this.classGroupPlanService = classGroupPlanService;
    }


    @RequireRole(RoleType.ADMIN)
    @PostMapping("/planos/cadastrar")
    public ResponseEntity<Void> addPlanToClassGroup(@RequestBody @Valid CreateClassGroupPlanDTO  requestDTO) {
        classGroupPlanService.addPlanToClassGroup(requestDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/planos/{classGroupId}")
    public ResponseEntity<List<PlanResponseDTO>> getPlansToClassGroup(@PathVariable UUID classGroupId) {
        return ResponseEntity.ok(classGroupPlanService.getPlansToClassGroup(classGroupId));
    }

    @RequireRole(RoleType.ADMIN)
    @DeleteMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> removePlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupPlanService.removePlanToClassGroup(planId, classGroupId);
        return ResponseEntity.noContent().build();
    }
}

package com.framework.modules.metric.controller;

import com.framework.common.enums.RoleType;
import com.framework.common.security.RequireRole;
import com.framework.modules.metric.dto.request.MetricTypeRequestDTO;
import com.framework.modules.metric.dto.response.MetricTypeResponseDTO;
import com.framework.modules.metric.service.MetricTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/metrica/tipo")
public class MetricTypeController {

    private final MetricTypeService metricTypeService;

    public MetricTypeController(MetricTypeService metricTypeService) {
        this.metricTypeService = metricTypeService;
    }

    @RequireRole(RoleType.ADMIN)
    @PostMapping("/cadastrar")
    public ResponseEntity<MetricTypeResponseDTO> createMetricType(@RequestBody MetricTypeRequestDTO requestDTO) {
        MetricTypeResponseDTO response = metricTypeService.createMetricType(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricTypeResponseDTO> getMetricTypeById(@PathVariable UUID id) {
        MetricTypeResponseDTO response = metricTypeService.getMetricTypeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<MetricTypeResponseDTO>> getAllMetricType() {
        List<MetricTypeResponseDTO> list = metricTypeService.getAllMetricsType();
        return ResponseEntity.ok(list);
    }

    @RequireRole(RoleType.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<MetricTypeResponseDTO> updateMetricType(@PathVariable UUID id,
            @RequestBody MetricTypeRequestDTO requestDTO) {
        MetricTypeResponseDTO response = metricTypeService.updateMetricType(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @RequireRole(RoleType.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableMetricType(@PathVariable UUID id) {
        metricTypeService.disableMetricType(id);
        return ResponseEntity.noContent().build();
    }

    @RequireRole(RoleType.ADMIN)
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> enableMetricType(@PathVariable UUID id) {
        metricTypeService.enableMetricType(id);
        return ResponseEntity.noContent().build();
    }
}
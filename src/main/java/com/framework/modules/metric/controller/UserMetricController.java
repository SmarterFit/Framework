package com.framework.modules.metric.controller;


import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.metric.dto.request.ImportMetricRequestDTO;
import com.framework.modules.metric.dto.request.MetricDataRequestDTO;
import com.framework.modules.metric.dto.response.ImportResultResponseDTO;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/perfis/metricas")
public class UserMetricController {

    private final UserMetricService userMetricService;

    public UserMetricController(UserMetricService userMetricService) {
        this.userMetricService = userMetricService;
    }


    @PostMapping("/import")
    public ResponseEntity<ImportResultResponseDTO> importMetrics(
            @RequestParam("file") MultipartFile file,
            @RequestBody  @Valid ImportMetricRequestDTO importMetricRequestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {

        ImportResultResponseDTO result = userMetricService.importMetrics(file, importMetricRequestDTO, requesterId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listar/{metricTypeId}")
    public ResponseEntity<List<AbstractMetricRecord>> getMetricsByProfileAndType(
             @RequestParam("metricTypeId") UUID metricTypeId,
            @RequestHeader("X-User-Id") UUID requesterId) {

        List<AbstractMetricRecord> result = userMetricService.getMetricsByProfileAndType(requesterId, metricTypeId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/adicionar")
    public ResponseEntity<AbstractMetricRecord> addMetric(
            @RequestBody @Valid MetricDataRequestDTO metricDataRequestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {

        AbstractMetricRecord result = userMetricService.addMetric(requesterId, metricDataRequestDTO);
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/remover/{metricId}")
    public ResponseEntity<AbstractMetricRecord> removeMetric(@RequestParam("metricTypeId") UUID metricTypeId) {

        userMetricService.removeMetric(metricTypeId);
        return ResponseEntity.noContent().build();
    }

}



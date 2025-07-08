package com.framework.modules.metric.controller;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.modules.metric.dto.request.MetricDataRequestDTO;
import com.framework.modules.metric.dto.response.ImportResultResponseDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
import com.framework.modules.metric.service.UserMetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/metrica")
public class UserMetricController {

    private final UserMetricService userMetricService;

    public UserMetricController(UserMetricService userMetricService) {
        this.userMetricService = userMetricService;
    }

    @PostMapping("/importar")
    public ResponseEntity<ImportResultResponseDTO> importMetrics(
            @RequestParam("file") MultipartFile file,
            @RequestParam("metricType") String metricType,
            @RequestHeader("X-User-Id") UUID requesterId) {

        ImportResultResponseDTO result = userMetricService.importMetrics(file, metricType, requesterId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listar/tipo/id/{metricTypeId}")
    public ResponseEntity<List<MetricDataResponseDTO>> getMetricsByProfileAndType(
            @PathVariable("metricTypeId") UUID metricTypeId,
            @RequestHeader("X-User-Id") UUID requesterId) {

        List<MetricDataResponseDTO> result = userMetricService.getMetricsByProfileAndType(requesterId, metricTypeId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/listar/tipo/nome/{nameMetricType}")
    public ResponseEntity<List<MetricDataResponseDTO>> getMetricsByProfileAndTypeByName(
            @PathVariable("nameMetricType") String nameMetricType,
            @RequestHeader("X-User-Id") UUID requesterId) {

        List<MetricDataResponseDTO> result = userMetricService.getMetricsByProfileAndTypeByName(requesterId,
                nameMetricType);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<MetricDataResponseDTO> addMetric(
            @RequestBody @Valid MetricDataRequestDTO metricDataRequestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {

        MetricDataResponseDTO result = userMetricService.addMetric(requesterId, metricDataRequestDTO);
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/remover/{metricId}")
    public ResponseEntity<AbstractMetricRecord> removeMetric(@RequestParam("metricTypeId") UUID metricTypeId) {

        userMetricService.removeMetric(metricTypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/historico/{metricTypeId}")
    public ResponseEntity<List<MetricDataResponseDTO>> getMetricHistory(@PathVariable UUID metricTypeId,
            @RequestHeader("X-User-Id") UUID requesterId) {
        List<MetricDataResponseDTO> history = userMetricService.getMetricHistory(requesterId, metricTypeId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/listar/ultimos")
    public ResponseEntity<List<MetricDataResponseDTO>> getLastsMetricsByProfile(
            @RequestHeader("X-User-Id") UUID requesterId) {
        List<MetricDataResponseDTO> history = userMetricService.getLastsMetricsByProfile(requesterId);
        return ResponseEntity.ok(history);
    }

}

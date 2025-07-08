package com.framework.modules.metric.service;

import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.modules.metric.dto.request.MetricTypeRequestDTO;
import com.framework.modules.metric.dto.response.MetricTypeResponseDTO;
import com.framework.modules.metric.mapper.MetricTypeMapper;
import com.framework.modules.metric.repository.MetricTypeRepository;
import com.framework.modules.metric.validation.MetricTypeValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MetricTypeService {

    private final MetricTypeRepository metricTypeRepository;
    private final MetricTypeValidation metricTypeValidation;

    public MetricTypeService(MetricTypeRepository metricTypeRepository,
            MetricTypeValidation metricTypeValidation) {
        this.metricTypeRepository = metricTypeRepository;
        this.metricTypeValidation = metricTypeValidation;
    }

    @Transactional
    public MetricTypeResponseDTO createMetricType(MetricTypeRequestDTO requestDTO) {
        metricTypeValidation.validateNameUniqueness(requestDTO.getType());

        MetricType metricType = MetricTypeMapper.toEntity(requestDTO);
        metricTypeRepository.save(metricType);

        return MetricTypeMapper.toResponse(metricType);
    }

    @Transactional(readOnly = true)
    public MetricTypeResponseDTO getMetricTypeById(UUID id) {
        MetricType metricType = metricTypeValidation.validateMetricTypeById(id);
        return MetricTypeMapper.toResponse(metricType);
    }

    @Transactional(readOnly = true)
    public List<MetricTypeResponseDTO> getAllMetricsType() {
        List<MetricType> metricTypes = metricTypeRepository.findAll();

        return metricTypes.stream().map(MetricTypeMapper::toResponse).toList();
    }

    @Transactional
    public MetricTypeResponseDTO updateMetricType(UUID id, MetricTypeRequestDTO requestDTO) {
        MetricType metricType = metricTypeValidation.validateMetricTypeById(id);

        metricType = MetricTypeMapper.toEntity(requestDTO, metricType);
        metricTypeRepository.save(metricType);

        return MetricTypeMapper.toResponse(metricType);
    }

    @Transactional
    public void disableMetricType(UUID id) {
        MetricType metricType = metricTypeValidation.validateMetricTypeById(id);
        metricType.setEnabled(false);
        metricTypeRepository.save(metricType);
    }

    @Transactional
    public void enableMetricType(UUID id) {
        MetricType metricType = metricTypeValidation.validateMetricTypeById(id);
        metricType.setEnabled(true);
        metricTypeRepository.save(metricType);
    }
}
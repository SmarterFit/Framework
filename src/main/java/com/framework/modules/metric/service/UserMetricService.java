package com.framework.modules.metric.service;

import com.framework.framework.usermetric.validation.AbstractMetricValidation;
import com.framework.modules.metric.dto.request.ImportMetricRequestDTO;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.request.MetricDataRequestDTO;
import com.framework.modules.metric.dto.response.ImportResultResponseDTO;
import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.usermetric.importer.ImportHandlerRegistry;
import com.framework.framework.usermetric.importer.handler.MetricDataImporterHandler;
import com.framework.framework.usermetric.metric.MetricHandlerRegistry;
import com.framework.framework.usermetric.metric.handler.MetricHandler;
import com.framework.modules.metric.repository.MetricTypeRepository;
import com.framework.modules.metric.repository.UserMetricRepository;
import com.framework.modules.metric.validation.MetricTypeValidation;
import com.framework.modules.metric.validation.UserMetricValidation;
import com.framework.modules.useraccess.entity.Profile;
import com.framework.modules.useraccess.validation.ProfileValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserMetricService {
    private final UserMetricRepository userMetricRepository;
    private final UserMetricValidation userMetricValidation;
    private final MetricTypeValidation metricTypeValidation;
    private final ProfileValidation profileValidation;
    private final AbstractMetricValidation abstractMetricValidation;

    public UserMetricService(UserMetricRepository userMetricRepository,
                             UserMetricValidation importDataMetricValidation,
                             MetricTypeValidation metricTypeValidation,
                             ProfileValidation profileValidation,
                             AbstractMetricValidation abstractMetricValidation) {
        this.userMetricRepository = userMetricRepository;
        this.userMetricValidation = importDataMetricValidation;
        this.metricTypeValidation = metricTypeValidation;
        this.profileValidation = profileValidation;
        this.abstractMetricValidation = abstractMetricValidation;
    }


    @Transactional
    public ImportResultResponseDTO importMetrics(MultipartFile file, ImportMetricRequestDTO importMetricRequestDTO, UUID requesterId) {

        Profile profile = profileValidation.validateProfileById(requesterId);
        MetricType metricType = metricTypeValidation.findMetricByType(importMetricRequestDTO.getMetricType());
        MetricDataImporterHandler importerHandler = userMetricValidation.getImporter(importMetricRequestDTO.getSourceType());
        List<MetricDataDTO> metricsDataDTO = importerHandler.parseFile(file);
        MetricHandler metricHandler = userMetricValidation.getMetricHandler(importMetricRequestDTO.getMetricType());

        List<AbstractMetricRecord> metrics = metricsDataDTO.stream()
                .map(metricDataDTO -> {
                    return  metricHandler.handle(metricDataDTO,
                            metricType,
                            profile,
                            importMetricRequestDTO.getSourceType());
                })
                .toList();

        userMetricRepository.saveAll(metrics);

        return ImportResultResponseDTO.builder()
                .totalRecords(metrics.size())
                .errorMessages(metricHandler.alerts())
                .build();
    }

    @Transactional
    public List<AbstractMetricRecord> getMetricsByProfileAndType(UUID profileId, UUID metricTypeId) {
        profileValidation.validateProfileByIdOrThrow(profileId);
        MetricType type = metricTypeValidation.findMetricById(metricTypeId);
        return userMetricRepository.findByProfileIdAndMetricTypeId(profileId, type.getId());
    }

    @Transactional
    public AbstractMetricRecord addMetric(UUID userId, MetricDataRequestDTO requestDTO) {
        Profile profile = profileValidation.validateProfileById(userId);

        MetricType metricType = metricTypeValidation.findMetricByType(requestDTO.getMetricType());
        MetricHandler handler = userMetricValidation.getMetricHandler(requestDTO.getMetricType());

        AbstractMetricRecord metricRecord = handler.handle(new MetricDataDTO(requestDTO.getData()),
                metricType,
                profile,
                requestDTO.getSource());


        return userMetricRepository.save(metricRecord);
    }

    @Transactional
    public void removeMetric(UUID metricId) {
        AbstractMetricRecord metricRecord = abstractMetricValidation.findAbstractMetricRecord(metricId);
        userMetricRepository.delete(metricRecord);
    }



}

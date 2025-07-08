package com.framework.modules.metric.service;

import com.framework.framework.usermetric.entity.generic.AbstractMetricRecord;
import com.framework.framework.usermetric.entity.generic.MetricType;
import com.framework.framework.importer.handler.MetricDataImporterHandler;
import com.framework.framework.usermetric.handler.MetricHandler;
import com.framework.framework.usermetric.validation.AbstractMetricValidation;
import com.framework.framework.usermetric.entity.MetricProcessResult;
import com.framework.framework.importer.validation.FileTypeValidator;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import com.framework.modules.metric.dto.request.MetricDataRequestDTO;
import com.framework.modules.metric.dto.response.ImportResultResponseDTO;
import com.framework.modules.metric.dto.response.MetricDataResponseDTO;
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
    private final FileTypeValidator fileTypeValidator;

    public UserMetricService(UserMetricRepository userMetricRepository,
                             UserMetricValidation importDataMetricValidation,
                             MetricTypeValidation metricTypeValidation,
                             ProfileValidation profileValidation,
                             AbstractMetricValidation abstractMetricValidation,
                             FileTypeValidator fileTypeValidator) {
        this.userMetricRepository = userMetricRepository;
        this.userMetricValidation = importDataMetricValidation;
        this.metricTypeValidation = metricTypeValidation;
        this.profileValidation = profileValidation;
        this.abstractMetricValidation = abstractMetricValidation;
        this.fileTypeValidator = fileTypeValidator;

    }


    @Transactional
    public ImportResultResponseDTO importMetrics(MultipartFile file, String type, UUID requesterId) {

        Profile profile = profileValidation.validateProfileById(requesterId);
        MetricType metricType = metricTypeValidation.findEnabledMetricByType(type);

        String sourceType = fileTypeValidator.validateAndGetImportMethod(file);

        MetricDataImporterHandler importerHandler = userMetricValidation.getImporter(sourceType);
        List<MetricDataDTO> metricsDataDTO = importerHandler.parseFile(file);

        MetricHandler metricHandler = userMetricValidation.getMetricHandler(type);

        List<MetricProcessResult> metrics = metricsDataDTO.stream()
                .map(metricDataDTO -> metricHandler.handle(
                        metricDataDTO,
                        metricType,
                        profile,
                        sourceType))
                .toList();

        userMetricRepository.saveAll(metrics.stream().map(MetricProcessResult::getRecord).toList());

        List<String> allAlerts = metrics.stream()
                .flatMap(result -> result.getAlerts().stream())
                .toList();


        return ImportResultResponseDTO.builder()
                .totalRecords(metrics.size())
                .errorMessages(allAlerts)
                .build();
    }


    @Transactional
    public List<MetricDataResponseDTO> getMetricsByProfileAndType(UUID profileId, UUID metricTypeId) {
        profileValidation.validateProfileByIdOrThrow(profileId);
        MetricType metricType = metricTypeValidation.findMetricById(metricTypeId);
        MetricHandler handler = userMetricValidation.getMetricHandler(metricType.getType());
        List<AbstractMetricRecord> list = userMetricRepository.findByProfileIdAndMetricTypeId(profileId, metricType.getId());

        return list.stream()
                .map(handler::toResponseDTO)
                .toList();
    }


    @Transactional
    public List<MetricDataResponseDTO> getMetricsByProfileAndTypeByName(UUID profileId, String metricTypeName) {
        profileValidation.validateProfileByIdOrThrow(profileId);
        MetricType metricType = metricTypeValidation.findMetricByType(metricTypeName);
        MetricHandler handler = userMetricValidation.getMetricHandler(metricType.getType());
        List<AbstractMetricRecord> list = userMetricRepository.findByProfileIdAndMetricTypeId(profileId, metricType.getId());

        return list.stream()
                .map(handler::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MetricDataResponseDTO> getLastsMetricsByProfile(UUID profileId) {
        profileValidation.validateProfileByIdOrThrow(profileId);
        List<AbstractMetricRecord> list = userMetricRepository.findLastsByProfileId(profileId);
        return list.stream().map(record -> {
            MetricHandler handler = userMetricValidation.getMetricHandler(record.getMetricType().getType());
            return handler.toResponseDTO(record);
        }).toList();
    }

    @Transactional
    public MetricDataResponseDTO addMetric(UUID userId, MetricDataRequestDTO requestDTO) {
        Profile profile = profileValidation.validateProfileById(userId);

        MetricType metricType = metricTypeValidation.findEnabledMetricByType(requestDTO.getMetricType());
        MetricHandler handler = userMetricValidation.getMetricHandler(requestDTO.getMetricType());

        MetricProcessResult result = handler.handle(new MetricDataDTO(requestDTO.getData()),
                metricType,
                profile,
                requestDTO.getSource());

        AbstractMetricRecord record = userMetricRepository.save(result.getRecord());
        return handler.toResponseDTO(record);
    }

    @Transactional
    public void removeMetric(UUID metricId) {
        AbstractMetricRecord metricRecord = abstractMetricValidation.findAbstractMetricRecord(metricId);
        userMetricRepository.delete(metricRecord);
    }

    @Transactional
    public List<MetricDataResponseDTO> getMetricHistory(UUID profileId, UUID metricTypeId) {
        profileValidation.validateProfileByIdOrThrow(profileId);
        MetricType metricType = metricTypeValidation.findMetricById(metricTypeId);
        MetricHandler handler = userMetricValidation.getMetricHandler(metricType.getType());

        List<AbstractMetricRecord> records = userMetricRepository
                                                .findByProfileIdAndMetricTypeIdOrderByCreatedAtAsc(profileId, metricTypeId);

        return records.stream()
                .map(handler::toResponseDTO)
                .toList();
    }
}

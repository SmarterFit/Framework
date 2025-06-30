package com.framework.framework.importer.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.common.enums.SourceType;
import com.framework.common.exceptions.BusinessException;
import com.framework.modules.metric.dto.request.MetricDataDTO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JSONMetricDataImporter implements MetricDataImporterHandler {

    private final ObjectMapper objectMapper;

    public JSONMetricDataImporter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public List<MetricDataDTO> parseFile(MultipartFile file) {
        try {
            return Arrays.asList(objectMapper.readValue(file.getInputStream(), MetricDataDTO[].class));
        } catch (IOException e) {
            throw new BusinessException("Invalid JSON file format");
        }
    }

    @Override
    public boolean supports(SourceType sourceType) {
        return sourceType == SourceType.JSON;
    }

    @Override
    public String getImportMethodName() {
        return "JSON";
    }
}

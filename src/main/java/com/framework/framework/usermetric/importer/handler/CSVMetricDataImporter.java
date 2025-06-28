package com.framework.framework.usermetric.importer.handler;


import com.framework.common.enums.SourceType;
import com.framework.common.exceptions.BusinessException;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVMetricDataImporter implements MetricDataImporterHandler {

    @Override
    public List<MetricDataDTO> parseFile(MultipartFile file) {
        List<MetricDataDTO> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord record : csvParser) {
                MetricDataDTO dto = new MetricDataDTO();
//                dto.setMetricType(record.get("metricType"));
                result.add(dto);
            }

        } catch (IOException e) {
            throw new BusinessException("Failed to parse CSV file", e);
        } catch (Exception e) {
            throw new BusinessException("Invalid data format in CSV file", e);
        }

        return result;
    }

    @Override
    public boolean supports(SourceType sourceType) {
        return sourceType == SourceType.CSV;
    }

    @Override
    public String getImportMethodName() {
        return "CSV";
    }
}

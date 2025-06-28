package com.framework.framework.usermetric.importer.handler;


import com.framework.common.enums.SourceType;
import com.framework.modules.metric.dto.request.MetricDataDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MetricDataImporterHandler {

    public List<MetricDataDTO> parseFile(MultipartFile file);

    public boolean supports(SourceType sourceType);

    public String getImportMethodName();
}

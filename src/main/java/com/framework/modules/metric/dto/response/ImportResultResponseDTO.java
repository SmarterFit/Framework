package com.framework.modules.metric.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ImportResultResponseDTO {
    private int totalRecords;
    private List<String> errorMessages;
}

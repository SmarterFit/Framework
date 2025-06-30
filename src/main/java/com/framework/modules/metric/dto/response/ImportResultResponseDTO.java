package com.framework.modules.metric.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportResultResponseDTO {
    private int totalRecords;
    private List<String> errorMessages;
}

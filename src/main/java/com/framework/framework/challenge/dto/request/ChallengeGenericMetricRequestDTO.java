package com.framework.framework.challenge.dto.request;

import com.framework.modules.metric.dto.request.MetricDataDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeGenericMetricRequestDTO {

    @NotNull(message = "Challenge quest ID is required")
    private UUID challengeQuestId;
    private MetricDataDTO metricDataDTO;
}

package com.framework.modules.challenge.dto.request.challengeDay;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDayRequestCreateDTO {

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @NotNull(message = "Trail id is required")
    private UUID trailId;

    private List<UUID> steps;

    @NotNull(message = "Challenge trail id is required")
    private UUID challengeTrailId;
}

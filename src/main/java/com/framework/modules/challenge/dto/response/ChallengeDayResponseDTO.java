package com.framework.modules.challenge.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDayResponseDTO {
    private UUID id;
    private LocalDate date;
    private List<ChallengeStepResponseDTO> steps;
}

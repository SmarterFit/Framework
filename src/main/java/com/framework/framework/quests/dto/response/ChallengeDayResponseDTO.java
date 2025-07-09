package com.framework.framework.quests.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDayResponseDTO {
    private UUID id;
    private LocalDateTime date;
    private List<ChallengeStepResponseDTO> steps;
}

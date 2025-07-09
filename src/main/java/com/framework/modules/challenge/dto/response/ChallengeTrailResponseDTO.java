package com.framework.modules.challenge.dto.response;

import com.framework.modules.challenge.dto.request.ChallengeQuestRequestDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeTrailResponseDTO {
    private UUID id;
    private ChallengeQuestResponseDTO quest;
    private List<ChallengeDayResponseDTO> days;
}
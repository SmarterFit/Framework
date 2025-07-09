package com.framework.framework.quests.dto.response;

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
    private PersonalQuestResponseDTO quest;
    private List<ChallengeDayResponseDTO> days;
}
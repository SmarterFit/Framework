package com.framework.framework.quests.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeStepResponseDTO {
    private UUID id;
    private String description;
    private boolean completed;
}
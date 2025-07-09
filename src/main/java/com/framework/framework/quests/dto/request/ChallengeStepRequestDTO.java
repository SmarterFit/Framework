package com.framework.framework.quests.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeStepRequestDTO {
    private String description;
    private boolean completed;
    private UUID dayId;
}

package com.framework.framework.quests.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeTrailRequestDTO {
    private UUID questId;
}

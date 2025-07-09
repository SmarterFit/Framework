package com.framework.modules.challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeTrailRequestDTO {

    @NotNull(message = "Challenge quest ID is required")
    private UUID challengeQuestId;

    private List<UUID> challengeDayIds;
}

package com.framework.modules.challenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeStepRequestDTO {

    @NotBlank(message = "Description is required")
    private String description;

    private boolean completed;

    @NotNull(message = "Challenge day id is required")
    private UUID challengeDayId;
}

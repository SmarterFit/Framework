package com.framework.framework.quests.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDayRequestDTO {
    private LocalDateTime date;
    private UUID trailId;
}

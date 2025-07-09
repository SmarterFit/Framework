package com.framework.modules.challenge.dto.request.challengeDay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.modules.challenge.entity.ChallengeStep;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeDayRequestUpdateDTO {

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;


}

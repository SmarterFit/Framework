package com.framework.modules.challenge.dto.request.challengeDay;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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

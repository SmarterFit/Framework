package com.framework.modules.classgroup.dto.request.classsession;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.common.enums.SessionStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateClassSessionRequestDTO {

    private String description;

    @NotNull(message = "Start time is required.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;

    private SessionStatus status;

    @Min(value = 1, message = "Minimum capacity must be 1.")
    @Max(value = 50, message = "Maximum capacity must be 50.")
    private Integer capacity;
}

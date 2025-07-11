package com.framework.modules.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.framework.common.enums.ExperienceLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeQuestRequestDTO {

    @NotNull(message = "Metric type is required")
    private UUID metricTypeId;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must have at most 100 characters")
    private String title;

    @NotBlank(message = "Challenge type is required")
    @Size(max = 50, message = "Challenge type must have at most 50 characters")
    private String challengeType;

    private ExperienceLevel experienceLevel;

    @NotNull(message = "Days of week are required")
    @Size(min = 1, message = "At least one day of the week must be selected")
    private List<DayOfWeek> daysOfWeek;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must have at most 500 characters")
    private String description;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
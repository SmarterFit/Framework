package com.framework.framework.usermetric.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationCreditDTO {

    @NotBlank(message = "O nome do curso é obrigatório.")
    private String courseName;

    @NotNull(message = "A data de conclusão é obrigatória.")
    private LocalDate completionDate;

    @Positive(message = "A carga horária deve ser maior que zero.")
    private double hours;

    @NotBlank(message = "A instituição é obrigatória.")
    private String institution;
}

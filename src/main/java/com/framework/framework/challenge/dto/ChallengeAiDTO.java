package com.framework.framework.challenge.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChallengeAiDTO {
    String day;
    List<TaskDTO> tasks;
}

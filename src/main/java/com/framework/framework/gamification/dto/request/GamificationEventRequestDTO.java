package com.framework.framework.gamification.dto.request;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GamificationEventRequestDTO {
   private String eventType;
   private UUID userId;
   private Map<String, Object> details;
}

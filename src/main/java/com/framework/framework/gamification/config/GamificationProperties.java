package com.framework.framework.gamification.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "gamification")
public class GamificationProperties {
   private boolean enabled;
   private List<String> events;
}

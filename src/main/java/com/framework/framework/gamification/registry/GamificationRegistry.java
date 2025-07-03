package com.framework.framework.gamification.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.config.GamificationProperties;
import com.framework.framework.gamification.handler.GamificationEventHandler;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GamificationRegistry {
   private boolean enabled;
   private Map<String, GamificationEventHandler> gamificationHandlers = new HashMap<>();

   public GamificationRegistry(GamificationProperties gamificationProperties,
         List<GamificationEventHandler> handlers) {
      this.enabled = gamificationProperties.isEnabled();
      List<String> events = gamificationProperties.getEvents();

      for (GamificationEventHandler handler : handlers) {
         if (events.contains(handler.getEventType())) {
            gamificationHandlers.put(handler.getEventType(), handler);
         }
      }
   }

   public GamificationEventHandler getHandler(String eventType) {
      return gamificationHandlers.get(eventType);
   }
}

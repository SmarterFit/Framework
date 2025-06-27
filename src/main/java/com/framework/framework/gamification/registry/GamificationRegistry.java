package com.framework.framework.gamification.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.framework.framework.gamification.config.GamificationProperties;
import com.framework.framework.gamification.processor.GamificationEventProcessor;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class GamificationRegistry {
   private boolean enabled;
   private Map<String, GamificationEventProcessor> gamificationProcessors = new HashMap<>();

   public GamificationRegistry(GamificationProperties gamificationProperties,
         List<GamificationEventProcessor> processors) {
      this.enabled = gamificationProperties.isEnabled();
      List<String> events = gamificationProperties.getEvents();

      for (GamificationEventProcessor processor : processors) {
         System.out.println("Processor: " + processor.getEventType());
         if (events.contains(processor.getEventType())) {
            gamificationProcessors.put(processor.getEventType(), processor);
         }
      }
   }

   public GamificationEventProcessor getProcessor(String eventType) {
      return gamificationProcessors.get(eventType);
   }
}

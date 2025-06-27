package com.framework.framework.gamification.event;

import org.springframework.context.ApplicationEvent;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;

import lombok.Getter;

@Getter
public class GamificationEvent extends ApplicationEvent {
   private GamificationEventRequestDTO request;

   public GamificationEvent(GamificationEventRequestDTO request) {
      super(request);
      this.request = request;
   }
}

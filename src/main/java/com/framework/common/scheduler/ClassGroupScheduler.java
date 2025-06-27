package com.framework.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.framework.modules.classgroup.service.ClassSessionService;

@Component
public class ClassGroupScheduler {
   private final ClassSessionService classSessionService;

   public ClassGroupScheduler(ClassSessionService classSessionService) {
      this.classSessionService = classSessionService;
   }

   @Scheduled(cron = "0 0 2 * * 0") // Every Sunday at 2 AM
   public void processClassSessions() {
      classSessionService.generateDailySessions();
   }
}

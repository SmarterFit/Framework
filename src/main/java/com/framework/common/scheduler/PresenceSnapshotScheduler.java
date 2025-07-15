package com.framework.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.framework.modules.checkin.service.PresenceSnapshotService;

@Component
public class PresenceSnapshotScheduler {
   private final PresenceSnapshotService presenceSnapshotService;

   public PresenceSnapshotScheduler(PresenceSnapshotService presenceSnapshotService) {
      this.presenceSnapshotService = presenceSnapshotService;
   }

   @Scheduled(cron = "0 */5 6-21 * * 1-6")
   public void registerPresence() {
      presenceSnapshotService.registerPresence();
   }
}

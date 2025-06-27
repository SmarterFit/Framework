/*
 * 
 * Created by Gabriel Henrique
 */
package com.framework.modules.checkin.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framework.modules.checkin.entity.PresenceSnapshot;

public interface PresenceSnapshotRepository extends JpaRepository<PresenceSnapshot, UUID> {
   List<PresenceSnapshot> findByCreatedAtBetweenOrderByCreatedAt(LocalDateTime startDate, LocalDateTime endDate);

   PresenceSnapshot findTopByOrderByCreatedAtDesc();
}
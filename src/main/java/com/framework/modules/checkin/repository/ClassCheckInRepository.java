package com.framework.modules.checkin.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.framework.modules.checkin.entity.ClassCheckIn;
import com.framework.modules.checkin.entity.id.ClassCheckInId;

@Repository
public interface ClassCheckInRepository extends JpaRepository<ClassCheckIn, ClassCheckInId> {
   List<ClassCheckIn> findByUserId(UUID userId);

   List<ClassCheckIn> findByClassSessionId(UUID classSessionId);

   Page<ClassCheckIn> findByUserIdOrderByCheckInTimeDesc(UUID userId, Pageable pageable);

   default List<ClassCheckIn> findLatestCheckInsByUser(UUID userId, int limit) {
      return findByUserIdOrderByCheckInTimeDesc(userId, PageRequest.of(0, limit)).getContent();
   }

}

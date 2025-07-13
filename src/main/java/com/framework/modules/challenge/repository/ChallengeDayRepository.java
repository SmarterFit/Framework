package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, UUID> {
    List<ChallengeDay> findByTrailId(UUID trailId);

    boolean existsByDate(LocalDate date);
}

package com.framework.framework.quests.repository;

import com.framework.framework.quests.entity.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, UUID> {
    List<ChallengeDay> findByTrailId(UUID trailId);
}

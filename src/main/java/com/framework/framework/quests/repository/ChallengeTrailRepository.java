package com.framework.framework.quests.repository;

import com.framework.framework.quests.entity.ChallengeTrail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeTrailRepository extends JpaRepository<ChallengeTrail, UUID> {
    List<ChallengeTrail> findByQuestId(UUID questId);
}

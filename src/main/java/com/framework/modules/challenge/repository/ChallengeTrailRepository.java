package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChallengeTrailRepository extends JpaRepository<ChallengeTrail, UUID> {

    Optional<ChallengeTrail> findByChallengeQuestId(UUID challengeQuestId);

    boolean existsByChallengeQuestId(UUID challengeQuestId);
}

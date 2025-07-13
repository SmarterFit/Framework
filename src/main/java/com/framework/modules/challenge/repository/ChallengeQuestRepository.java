package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeQuestRepository extends JpaRepository<ChallengeQuest, UUID> {
   List<ChallengeQuest> findAllByProfileId(UUID profileId);
}

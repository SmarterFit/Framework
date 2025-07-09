package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChallengeQuestRepository extends JpaRepository<ChallengeQuest, UUID> {

}

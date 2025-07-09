package com.framework.framework.quests.repository;

import com.framework.framework.quests.entity.ChallengeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeStepRepository extends JpaRepository<ChallengeStep, UUID> {
    List<ChallengeStep> findByDayId(UUID dayId);
}

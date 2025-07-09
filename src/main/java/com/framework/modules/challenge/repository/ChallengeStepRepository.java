package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeStepRepository extends JpaRepository<ChallengeStep, UUID> {
    List<ChallengeStep> findByDayId(UUID dayId);
}

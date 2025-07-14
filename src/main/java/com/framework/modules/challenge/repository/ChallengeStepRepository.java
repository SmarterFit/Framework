package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChallengeStepRepository extends JpaRepository<ChallengeStep, UUID> {
    List<ChallengeStep> findByDayId(UUID dayId);
}

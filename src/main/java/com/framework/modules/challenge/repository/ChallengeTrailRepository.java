package com.framework.modules.challenge.repository;

import com.framework.modules.challenge.entity.ChallengeTrail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChallengeTrailRepository extends JpaRepository<ChallengeTrail, UUID> {


}

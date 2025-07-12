package com.framework.framework.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.framework.framework.challenge.entity.ChallengeType;

import jakarta.transaction.Transactional;

@Repository
public interface ChallengeTypeRepository extends JpaRepository<ChallengeType, String> {
    Optional<ChallengeType> findByIdAndEnabledTrue(String id);

    Optional<ChallengeType> findByName(String name);

    List<ChallengeType> findAllByEnabledTrue();

    @Modifying
    @Transactional
    @Query("UPDATE ChallengeType m SET m.enabled = false WHERE m.name NOT IN :activeIds")
    void deactivateTypesNotIn(List<String> activeIds);
}

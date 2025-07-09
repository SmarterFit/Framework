package com.framework.framework.quests.repository;

import com.framework.framework.quests.entity.PersonalQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonalQuestRepository extends JpaRepository<PersonalQuest, UUID> {
    List<PersonalQuest> findByUserId(UUID userId);
}

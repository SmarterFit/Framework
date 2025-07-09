package com.framework.framework.quests.service;

import com.framework.framework.quests.dto.request.ChallengeTrailRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeTrailResponseDTO;
import com.framework.framework.quests.entity.ChallengeTrail;
import com.framework.framework.quests.mapper.ChallengeTrailMapper;
import com.framework.framework.quests.repository.ChallengeTrailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChallengeTrailService {

    private final ChallengeTrailRepository repository;

    public ChallengeTrailService(ChallengeTrailRepository repository) {
        this.repository = repository;
    }

    public ChallengeTrailResponseDTO create(ChallengeTrailRequestDTO request) {
        ChallengeTrail entity = ChallengeTrailMapper.toEntity(request);
        ChallengeTrail saved = repository.save(entity);
        return ChallengeTrailMapper.toResponseDTO(saved);
    }

    public ChallengeTrailResponseDTO update(UUID id, ChallengeTrailRequestDTO request) {
        ChallengeTrail existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeTrail not found"));

        var quest = existing.getQuest();
        if (!quest.getId().equals(request.getQuestId())) {
            var newQuest = new com.framework.framework.quests.entity.PersonalQuest();
            newQuest.setId(request.getQuestId());
            existing.setQuest(newQuest);
        }

        ChallengeTrail updated = repository.save(existing);
        return ChallengeTrailMapper.toResponseDTO(updated);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<ChallengeTrailResponseDTO> findByQuestId(UUID questId) {
        return repository.findByQuestId(questId).stream()
                .map(ChallengeTrailMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ChallengeTrailResponseDTO findById(UUID id) {
        ChallengeTrail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeTrail not found"));
        return ChallengeTrailMapper.toResponseDTO(entity);
    }
}
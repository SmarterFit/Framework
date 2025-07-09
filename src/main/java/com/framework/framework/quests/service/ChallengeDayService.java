package com.framework.framework.quests.service;

import com.framework.framework.quests.dto.request.ChallengeDayRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeDayResponseDTO;
import com.framework.framework.quests.entity.ChallengeDay;
import com.framework.framework.quests.mapper.ChallengeDayMapper;
import com.framework.framework.quests.repository.ChallengeDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChallengeDayService {

    private final ChallengeDayRepository repository;

    public ChallengeDayService(ChallengeDayRepository repository) {
        this.repository = repository;
    }

    public ChallengeDayResponseDTO create(ChallengeDayRequestDTO request) {
        ChallengeDay entity = ChallengeDayMapper.toEntity(request);
        ChallengeDay saved = repository.save(entity);
        return ChallengeDayMapper.toResponseDTO(saved);
    }

    public ChallengeDayResponseDTO update(UUID id, ChallengeDayRequestDTO request) {
        ChallengeDay existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeDay not found"));

        existing.setDate(request.getDate());

        var trail = existing.getTrail();
        if (!trail.getId().equals(request.getTrailId())) {
            var newTrail = new com.framework.framework.quests.entity.ChallengeTrail();
            newTrail.setId(request.getTrailId());
            existing.setTrail(newTrail);
        }

        ChallengeDay updated = repository.save(existing);
        return ChallengeDayMapper.toResponseDTO(updated);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<ChallengeDayResponseDTO> findByTrailId(UUID trailId) {
        return repository.findByTrailId(trailId).stream()
                .map(ChallengeDayMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ChallengeDayResponseDTO findById(UUID id) {
        ChallengeDay entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeDay not found"));
        return ChallengeDayMapper.toResponseDTO(entity);
    }
}

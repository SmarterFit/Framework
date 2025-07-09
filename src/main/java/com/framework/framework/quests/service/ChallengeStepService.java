package com.framework.framework.quests.service;

import com.framework.framework.quests.dto.request.ChallengeStepRequestDTO;
import com.framework.framework.quests.dto.response.ChallengeStepResponseDTO;
import com.framework.framework.quests.entity.ChallengeStep;
import com.framework.framework.quests.mapper.ChallengeStepMapper;
import com.framework.framework.quests.repository.ChallengeStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChallengeStepService {

    private final ChallengeStepRepository repository;

    public ChallengeStepService(ChallengeStepRepository repository) {
        this.repository = repository;
    }

    public ChallengeStepResponseDTO create(ChallengeStepRequestDTO request) {
        ChallengeStep entity = ChallengeStepMapper.toEntity(request);
        ChallengeStep saved = repository.save(entity);
        return ChallengeStepMapper.toResponseDTO(saved);
    }

    public ChallengeStepResponseDTO update(UUID id, ChallengeStepRequestDTO request) {
        ChallengeStep existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeStep not found"));

        existing.setDescription(request.getDescription());
        existing.setCompleted(request.isCompleted());

        var day = existing.getDay();
        if (!day.getId().equals(request.getDayId())) {
            var newDay = new com.framework.framework.quests.entity.ChallengeDay();
            newDay.setId(request.getDayId());
            existing.setDay(newDay);
        }

        ChallengeStep updated = repository.save(existing);
        return ChallengeStepMapper.toResponseDTO(updated);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<ChallengeStepResponseDTO> findByDayId(UUID dayId) {
        return repository.findByDayId(dayId).stream()
                .map(ChallengeStepMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ChallengeStepResponseDTO findById(UUID id) {
        ChallengeStep entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChallengeStep not found"));
        return ChallengeStepMapper.toResponseDTO(entity);
    }
}

package com.framework.modules.challenge.service;

import com.framework.modules.challenge.dto.request.ChallengeStepRequestDTO;
import com.framework.modules.challenge.dto.response.ChallengeStepResponseDTO;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.mapper.ChallengeStepMapper;
import com.framework.modules.challenge.repository.ChallengeStepRepository;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.validation.ChallengeDayValidation;
import com.framework.modules.challenge.validation.ChallengeStepValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChallengeStepService {

    private final ChallengeStepRepository repository;
    private final ChallengeDayValidation challengeDayValidation;
    private final ChallengeStepValidation challengeStepValidation;

    public ChallengeStepService(ChallengeStepRepository repository,
            ChallengeDayValidation challengeDayValidation,
            ChallengeStepValidation challengeStepValidation) {
        this.repository = repository;
        this.challengeDayValidation = challengeDayValidation;
        this.challengeStepValidation = challengeStepValidation;
    }

    public ChallengeStepResponseDTO create(ChallengeStepRequestDTO request) {
        ChallengeDay day = challengeDayValidation.validateChallengeDay(request.getChallengeDayId());

        ChallengeStep entity = ChallengeStepMapper.toEntity(request, day);
        ChallengeStep saved = repository.save(entity);
        return ChallengeStepMapper.toResponseDTO(saved);
    }

    public ChallengeStepResponseDTO update(UUID id, ChallengeStepRequestDTO request) {
        ChallengeStep step = challengeStepValidation.validateChallengeStep(id);

        ChallengeStep newStep = ChallengeStepMapper.toEntity(request, step.getDay(), step);
        return ChallengeStepMapper.toResponseDTO(newStep);
    }

    public ChallengeStepResponseDTO toggle(UUID id) {
        ChallengeStep step = challengeStepValidation.validateChallengeStep(id);
        step.setCompleted(!step.isCompleted());
        ChallengeStep updated = repository.save(step);
        return ChallengeStepMapper.toResponseDTO(updated);
    }

    public void delete(UUID id) {
        challengeStepValidation.validateChallengeStepExists(id);
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

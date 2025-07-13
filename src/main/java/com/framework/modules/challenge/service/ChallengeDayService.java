package com.framework.modules.challenge.service;

import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestCreateDTO;
import com.framework.modules.challenge.dto.request.challengeDay.ChallengeDayRequestUpdateDTO;
import com.framework.modules.challenge.dto.response.ChallengeDayResponseDTO;
import com.framework.modules.challenge.entity.ChallengeDay;
import com.framework.modules.challenge.mapper.ChallengeDayMapper;
import com.framework.modules.challenge.repository.ChallengeDayRepository;
import com.framework.modules.challenge.entity.ChallengeStep;
import com.framework.modules.challenge.entity.ChallengeTrail;
import com.framework.modules.challenge.validation.ChallengeDayValidation;
import com.framework.modules.challenge.validation.ChallengeStepValidation;
import com.framework.modules.challenge.validation.ChallengeTrailValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChallengeDayService {

    private final ChallengeDayRepository repository;
    private final ChallengeDayValidation challengeDayValidation;
    private final ChallengeStepValidation challengeStepValidation;
    private final ChallengeTrailValidation challengeTrailValidation;

    public ChallengeDayService(ChallengeDayRepository repository,
            ChallengeDayValidation challengeDayValidation,
            ChallengeStepValidation challengeStepValidation,
            ChallengeTrailValidation challengeTrailValidation) {
        this.repository = repository;
        this.challengeDayValidation = challengeDayValidation;
        this.challengeStepValidation = challengeStepValidation;
        this.challengeTrailValidation = challengeTrailValidation;
    }

    public ChallengeDayResponseDTO create(ChallengeDayRequestCreateDTO request) {
        challengeDayValidation.alreadyExistChallengeDay(request.getDate());
        ChallengeTrail trail = challengeTrailValidation.validateChallengerTail(request.getTrailId());
        List<ChallengeStep> steps = new ArrayList<>();

        if (request.getSteps() != null) {
            steps = request.getSteps().stream()
                    .map(challengeStepValidation::validateChallengeStep)
                    .collect(Collectors.toList());
        }

        ChallengeDay entity = ChallengeDayMapper.toEntity(request, steps, trail);
        ChallengeDay saved = repository.save(entity);
        return ChallengeDayMapper.toResponseDTO(saved);
    }

    public ChallengeDayResponseDTO update(UUID id, ChallengeDayRequestUpdateDTO request) {
        ChallengeDay day = challengeDayValidation.validateChallengeDay(id);
        challengeDayValidation.alreadyExistChallengeDay(request.getDate());

        day = ChallengeDayMapper.toEntity(request, day);
        day = repository.save(day);

        return ChallengeDayMapper.toResponseDTO(day);

    }

    public void delete(UUID id) {
        challengeDayValidation.validateChallengeDayExists(id);
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

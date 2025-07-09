package com.framework.framework.quests.service;

import com.framework.framework.quests.dto.request.PersonalQuestRequestDTO;
import com.framework.framework.quests.dto.response.PersonalQuestResponseDTO;
import com.framework.framework.quests.entity.PersonalQuest;
import com.framework.framework.quests.mapper.PersonalQuestMapper;
import com.framework.framework.quests.repository.PersonalQuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonalQuestService {

    private final PersonalQuestRepository repository;

    public PersonalQuestService(PersonalQuestRepository repository) {
        this.repository = repository;
    }

    public PersonalQuestResponseDTO create(PersonalQuestRequestDTO request) {
        PersonalQuest entity = PersonalQuestMapper.toEntity(request);
        PersonalQuest saved = repository.save(entity);
        return PersonalQuestMapper.toResponseDTO(saved);
    }

    public PersonalQuestResponseDTO update(UUID id, PersonalQuestRequestDTO request) {
        PersonalQuest existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PersonalQuest not found"));
        existing.setName(request.getName());
        existing.setDomain(request.getDomain());
        existing.setDescription(request.getDescription());
        existing.setWeeklyFrequency(request.getWeeklyFrequency());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setMetricType(request.getMetricType());
        existing.setCompleted(request.isCompleted());

        PersonalQuest updated = repository.save(existing);
        return PersonalQuestMapper.toResponseDTO(updated);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<PersonalQuestResponseDTO> findAllByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
                .map(PersonalQuestMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PersonalQuestResponseDTO findById(UUID id) {
        PersonalQuest entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PersonalQuest not found"));
        return PersonalQuestMapper.toResponseDTO(entity);
    }
}
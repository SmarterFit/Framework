package com.framework.modules.classgroup.service;

import com.framework.modules.classgroup.dto.request.classgroup.schedule.CreateClassGroupScheduleRequestDTO;
import com.framework.modules.classgroup.dto.response.ClassGroupScheduleResponseDTO;
import com.framework.modules.classgroup.entity.ClassGroup;
import com.framework.modules.classgroup.entity.ClassGroupSchedule;
import com.framework.modules.classgroup.mapper.ClassGroupScheduleMapper;
import com.framework.modules.classgroup.repository.ClassGroupScheduleRepository;
import com.framework.modules.classgroup.validation.ClassGroupScheduleValidation;
import com.framework.modules.classgroup.validation.ClassGroupValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClassGroupScheduleService {
    private final ClassGroupScheduleRepository classGroupScheduleRepository;
    private final ClassGroupScheduleValidation classGroupScheduleValidation;
    private final ClassGroupValidation classGroupValidation;

    public ClassGroupScheduleService(ClassGroupScheduleRepository classGroupScheduleRepository,
            ClassGroupScheduleValidation classGroupScheduleValidation,
            ClassGroupValidation classGroupValidation) {

        this.classGroupScheduleRepository = classGroupScheduleRepository;
        this.classGroupScheduleValidation = classGroupScheduleValidation;
        this.classGroupValidation = classGroupValidation;
    }

    @Transactional
    public ClassGroupScheduleResponseDTO createClassGroupSchedule(CreateClassGroupScheduleRequestDTO requestDTO) {
        classGroupScheduleValidation.validateNoScheduleConflict(requestDTO);
        classGroupScheduleValidation.validateClassSchedulesDates(requestDTO.getStartTime(), requestDTO.getEndTime());

        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        ClassGroupSchedule classGroupSchedule = ClassGroupScheduleMapper.toEntity(requestDTO, classGroup);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(classGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    @Transactional(readOnly = true)
    public ClassGroupScheduleResponseDTO getClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        return ClassGroupScheduleMapper.toResponse(classGroupSchedule);
    }

    @Transactional(readOnly = true)
    public List<ClassGroupScheduleResponseDTO> getAllClassGroupSchedulesById(UUID classGroupId) {
        classGroupValidation.validateClassGroupById(classGroupId);

        List<ClassGroupSchedule> schedules = classGroupScheduleRepository.findAllByClassGroupId(classGroupId);

        return schedules.stream()
                .map(ClassGroupScheduleMapper::toResponse)
                .toList();
    }



    @Transactional
    public ClassGroupScheduleResponseDTO updateClassGroupScheduleById(UUID id,
            CreateClassGroupScheduleRequestDTO requestDTO) {
        classGroupScheduleValidation.validateNoScheduleConflict(requestDTO);
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);

        ClassGroupSchedule updatedClassGroupSchedule = ClassGroupScheduleMapper.toEntity(requestDTO,
                classGroup, classGroupSchedule);

        ClassGroupSchedule savedClassGroupSchedule = classGroupScheduleRepository.save(updatedClassGroupSchedule);

        return ClassGroupScheduleMapper.toResponse(savedClassGroupSchedule);
    }

    @Transactional
    public void deleteClassGroupScheduleById(UUID id) {
        ClassGroupSchedule classGroupSchedule = classGroupScheduleValidation.validateClassGroupScheduleById(id);
        classGroupScheduleRepository.delete(classGroupSchedule);
    }
}

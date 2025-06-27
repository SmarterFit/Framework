package com.framework.modules.classgroup.validation;

import com.framework.common.exceptions.ResourceAlreadyExistsException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.classgroup.entity.Modality;
import com.framework.modules.classgroup.repository.ModalityRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModalityValidation {

    private final ModalityRepository modalityRepository;

    public ModalityValidation(ModalityRepository classGroupRepository) {
        this.modalityRepository = classGroupRepository;
    }

    public Modality validateModalityById(UUID id) {
        return modalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modality not found."));
    }

    public void existsModalityByName(String name) {
        if (modalityRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Modality already exists");
        }
    }
}

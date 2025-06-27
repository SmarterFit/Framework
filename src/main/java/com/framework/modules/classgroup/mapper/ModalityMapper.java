package com.framework.modules.classgroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.classgroup.dto.request.modality.CreateModalityRequestDTO;
import com.framework.modules.classgroup.dto.response.ModalityResponseDTO;
import com.framework.modules.classgroup.entity.Modality;

public class ModalityMapper {

    private ModalityMapper() {
        // Private constructor to prevent instantiation
    }

    public static Modality toEntity(CreateModalityRequestDTO dto) {
        return toEntity(dto, new Modality());
    }

    public static Modality toEntity(CreateModalityRequestDTO dto, Modality modality) {
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found");
        }

        modality = GenericMapper.map(dto, modality);

        return modality;
    }

    public static ModalityResponseDTO toResponse(Modality modality) {
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found");
        }

        return GenericMapper.map(modality, ModalityResponseDTO.class);
    }

}

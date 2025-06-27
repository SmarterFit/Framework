package com.framework.modules.training.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.training.dto.request.EquipmentRequestDTO;
import com.framework.modules.training.dto.response.EquipmentResponseDTO;
import com.framework.modules.training.entity.Equipment;


public class EquipmentMapper {

    private EquipmentMapper() {
        // Private constructor to prevent instantiation
    }

    public static Equipment toEntity(EquipmentRequestDTO dto) {
        return toEntity(dto, new Equipment());
    }

    public static Equipment toEntity(EquipmentRequestDTO dto, Equipment equipment) {
        if (equipment == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        equipment = GenericMapper.map(dto, equipment);

        return equipment;
    }

    public static EquipmentResponseDTO toResponse(Equipment equipment) {
        if (equipment == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        return GenericMapper.map(equipment, EquipmentResponseDTO.class);
    }

}

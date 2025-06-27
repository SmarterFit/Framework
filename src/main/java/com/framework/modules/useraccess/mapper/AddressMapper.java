package com.framework.modules.useraccess.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.useraccess.dto.request.address.CreateAddressRequestDTO;
import com.framework.modules.useraccess.dto.response.AddressResponseDTO;
import com.framework.modules.useraccess.entity.Address;
import com.framework.modules.useraccess.entity.Profile;

public class AddressMapper {
    private AddressMapper() {
        // Private constructor to prevent instantiation
    }

    public static Address toEntity(CreateAddressRequestDTO dto, Profile profile) {
        return toEntity(dto, profile, new Address());
    }

    public static Address toEntity(CreateAddressRequestDTO dto, Profile profile, Address address) {
        if (address == null) {
            throw new ResourceNotFoundException("Address not found.");
        }
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found.");
        }

        address = GenericMapper.map(dto, address);
        address.setProfile(profile);

        return address;
    }

    public static AddressResponseDTO toResponse(Address address) {
        if (address == null) {
            throw new ResourceNotFoundException("Address not found.");
        }

        return GenericMapper.map(address, AddressResponseDTO.class);
    }
}

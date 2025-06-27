package com.framework.modules.classgroup.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.classgroup.dto.request.classevent.booking.CreateClassEventBookingRequestDTO;
import com.framework.modules.classgroup.dto.request.classevent.booking.UpdateClassEventBookingRequestDTO;
import com.framework.modules.classgroup.dto.response.ClassEventBookingResponseDTO;
import com.framework.modules.classgroup.entity.ClassEvent;
import com.framework.modules.classgroup.entity.ClassEventBooking;
import com.framework.modules.useraccess.entity.User;

public class ClassEventBookingMapper {

    private ClassEventBookingMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassEventBooking toEntity(CreateClassEventBookingRequestDTO dto,
            ClassEvent classEvent, User user) {
        return toEntity(dto, classEvent, user, new ClassEventBooking());
    }

    public static ClassEventBooking toEntity(CreateClassEventBookingRequestDTO dto,
            ClassEvent classEvent, User user, ClassEventBooking classEventBooking) {
        if (classEventBooking == null) {
            throw new ResourceNotFoundException("ClassEventBooking not found.");
        }
        if (classEvent == null) {
            throw new ResourceNotFoundException("ClassEvent not found.");
        }
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }

        classEventBooking = GenericMapper.map(dto, classEventBooking);
        classEventBooking.setClassEvent(classEvent);
        classEventBooking.setUser(user);

        return classEventBooking;
    }

    public static ClassEventBooking toEntityUpdateStatus(UpdateClassEventBookingRequestDTO dto,
            ClassEventBooking classEventBooking) {
        if (classEventBooking == null) {
            throw new ResourceNotFoundException("ClassEventBooking not found.");
        }

        classEventBooking = GenericMapper.map(dto, classEventBooking);

        return classEventBooking;
    }

    public static ClassEventBookingResponseDTO toResponse(ClassEventBooking classEventBooking) {
        if (classEventBooking == null) {
            throw new ResourceNotFoundException("ClassEventBooking not found.");
        }

        ClassEventBookingResponseDTO response = GenericMapper.map(classEventBooking,
                ClassEventBookingResponseDTO.class);
        response = response.toBuilder()
                .classEventId(classEventBooking.getClassEvent().getId())
                .userId(classEventBooking.getUser().getId())
                .build();


        return response;
    }

}

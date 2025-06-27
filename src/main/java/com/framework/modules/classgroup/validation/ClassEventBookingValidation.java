package com.framework.modules.classgroup.validation;

import com.framework.common.exceptions.ResourceAlreadyExistsException;
import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.modules.classgroup.entity.ClassEventBooking;
import com.framework.modules.classgroup.entity.id.ClassEventBookingId;
import com.framework.modules.classgroup.repository.ClassEventBookingRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassEventBookingValidation {

    private final ClassEventBookingRepository classEventBookingRepository;

    public ClassEventBookingValidation(ClassEventBookingRepository classEventBookingRepository) {
        this.classEventBookingRepository = classEventBookingRepository;
    }

    public ClassEventBooking validateClassEventBookingById(ClassEventBookingId id) {
        return classEventBookingRepository.findByUserIdAndClassEventId(id.getUser(), id.getClassEvent())
                .orElseThrow(() -> new ResourceNotFoundException("Class session booking not found with id: " + id));
    }

    public void validateClassEventBookingExists(UUID userId, UUID classEventId) {
        if (classEventBookingRepository.existsByUserIdAndClassEventId(userId, classEventId)) {
            throw new ResourceAlreadyExistsException("Class session booking already exists");
        }
    }

}

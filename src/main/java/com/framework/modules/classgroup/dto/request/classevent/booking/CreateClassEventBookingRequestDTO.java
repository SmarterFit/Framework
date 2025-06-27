package com.framework.modules.classgroup.dto.request.classevent.booking;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateClassEventBookingRequestDTO {
    @NotNull(message = "User ID  is required")
    private UUID userId;

    @NotNull(message = "Class group ID is required")
    private UUID classEventId;
}

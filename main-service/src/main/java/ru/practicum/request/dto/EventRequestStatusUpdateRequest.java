package ru.practicum.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ru.practicum.request.model.EventRequestStatus;

import java.util.Collection;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private Collection<Long> requestIds;

    @NotBlank
    private EventRequestStatus status;
}

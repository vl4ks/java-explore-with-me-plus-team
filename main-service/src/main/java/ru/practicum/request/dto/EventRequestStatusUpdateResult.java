package ru.practicum.request.dto;

import java.util.Collection;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class EventRequestStatusUpdateResult {
    private Collection<ParticipationRequestDto> confirmedRequests;

    private Collection<ParticipationRequestDto> rejectedRequests;
}

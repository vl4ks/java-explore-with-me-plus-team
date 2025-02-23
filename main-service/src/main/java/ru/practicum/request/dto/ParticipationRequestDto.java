package ru.practicum.request.dto;

import ru.practicum.request.model.EventRequestStatus;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ParticipationRequestDto {
    private String created;

    private Long event;

    private Long id;

    private Long requester;

    private EventRequestStatus status;
}

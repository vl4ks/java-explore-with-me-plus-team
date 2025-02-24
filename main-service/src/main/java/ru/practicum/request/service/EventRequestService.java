package ru.practicum.request.service;

import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.Collection;

public interface EventRequestService {

    public ParticipationRequestDto create(Long userId, Long eventId);

    public EventRequestStatusUpdateResult updateStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestsToUpdate);

    public ParticipationRequestDto cancel(Long userId, Long requestId);

    public Collection<ParticipationRequestDto> getByRequesterId(Long requesterId);

    public Collection<ParticipationRequestDto> getByEventId(Long eventInitiatorId, Long eventId);
}


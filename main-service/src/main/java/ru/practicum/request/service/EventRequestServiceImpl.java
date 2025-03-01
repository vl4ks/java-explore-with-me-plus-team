package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.EventRequestDtoMapper;
import ru.practicum.request.model.EventRequest;
import ru.practicum.request.model.EventRequestStatus;
import ru.practicum.request.storage.EventRequestRepository;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service("eventRequestServiceImpl")
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {
    private final UserService userService;
    private final EventService eventService;
    private final EventRequestRepository eventRequestRepository;
    private final EventRequestDtoMapper eventRequestDtoMapper;

    private EventRequest findByRequesterAndEvent(Long requesterId, Long eventId) {
        final EventRequest request = eventRequestRepository.findByRequesterAndEvent(requesterId, eventId);
        return request;
    }

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        final UserDto user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException("User with id=" + userId + " was not found");
        }
        final EventFullDto event = eventService.findById(null, eventId);
        if (event == null) {
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Initiator of event can't be the same with requester");
        }
        if (!event.getState().equals("PUBLISHED")) {
            throw new ConflictException("Can't send request to unpublished event");
        }
        if (event.getParticipantLimit() == null || event.getParticipantLimit() == 0 && event.getConfirmRequests() >= event.getConfirmRequests()) {
            throw new ConflictException("Limit of event can't be full or not exist");
        }
        final EventRequest request = new EventRequest(
                null,
                eventId,
                userId,
                event.getRequestModeration() ? EventRequestStatus.CONFIRMED : EventRequestStatus.PENDING,
                LocalDateTime.now()
        );
        final EventRequest createdRequest = eventRequestRepository.save(request);
        return eventRequestDtoMapper.mapToResponseDto(createdRequest);
    }

    private EventRequest updateStatus(Long requestId, EventRequestStatus status) {
        final EventRequest request = eventRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("Request with id=" + requestId + " was not found")
        );
        request.setStatus(status);
        final EventRequest updatedRequest = eventRequestRepository.save(request);
        return updatedRequest;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestsToUpdate) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        for (Long requestId : requestsToUpdate.getRequestIds()) {
            result.getConfirmedRequests().add(eventRequestDtoMapper.mapToResponseDto(updateStatus(requestId, requestsToUpdate.getStatus())));
        }
        return result;
    }

    @Override
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        final EventRequest request = eventRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("Request with id=" + requestId + " was not found")
        );
        if (!userId.equals(request.getRequesterId())) {
            throw new NotFoundException("Not owner (userId=" + userId + ") of request trying to cancel it (request=" + request + ")");
        }
        updateStatus(requestId, EventRequestStatus.CANCELLED);
        return eventRequestDtoMapper.mapToResponseDto(request);
    }

    @Override
    public Collection<ParticipationRequestDto> getByRequesterId(Long requesterId) {
        if (userService.findById(requesterId) == null) {
            throw new NotFoundException("User with id=" + requesterId + " was not found");
        }
        final Collection<EventRequest> requests = eventRequestRepository.findByRequesterId(requesterId);
        return requests.stream()
                .map(eventRequestDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<ParticipationRequestDto> getByEventId(Long eventInitiatorId, Long eventId) {
        if (userService.findById(eventInitiatorId) == null) {
            throw new NotFoundException("User with id=" + eventInitiatorId + " was not found");
        }
        if (eventService.findById(eventInitiatorId, eventId) == null) {
            throw new NotFoundException("Event with id=" + eventId + " was not found on user with id=" + eventInitiatorId);
        }
        final Collection<EventRequest> requests = eventRequestRepository.findByEventId(eventId);
        return requests.stream()
                .map(eventRequestDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

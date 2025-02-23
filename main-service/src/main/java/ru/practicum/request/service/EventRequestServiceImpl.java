package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    private final EventRequestRepository eventRequestRepository;
    private final EventRequestDtoMapper eventRequestDtoMapper;

    private EventRequest findByRequesterAndEvent(Long requesterId, Long eventId) {
        final EventRequest request = eventRequestRepository.findByRequesterAndEvent(requesterId, eventId);
        return request;
    }

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        final EventRequest request = new EventRequest(
                null,
                eventId,
                userId,
                EventRequestStatus.PENDING,
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
            new NotFoundException("Not owner (userId=" + userId + ") of request trying to cancel it (request=" + request + ")");
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
        final Collection<EventRequest> requests = eventRequestRepository.findByEventId(eventId);
        return requests.stream()
                .map(eventRequestDtoMapper::mapToResponseDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

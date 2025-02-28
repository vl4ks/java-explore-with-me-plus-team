package ru.practicum.event.service;

import ru.practicum.event.dto.*;

import java.util.Collection;
import java.util.List;

public interface EventService {

    public EventFullDto create(Long userId, NewEventDto eventDto);

    public Collection<EventShortDto> findAllByPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    public Collection<EventShortDto> findAllByPrivate(Long userId, Integer from, Integer size);

    public Collection<EventFullDto> findAllByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    public EventFullDto findById(Long userId, Long eventId, Boolean isPublic);

    public EventFullDto updateByPrivate(Long userId, Long eventId, UpdateEventUserRequest eventDto);

    public EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequest eventDto);
}

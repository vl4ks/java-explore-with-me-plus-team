package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserAndAdminRequest;

import java.util.Collection;
import java.util.List;

public interface EventService {

    public EventFullDto create(Long userId, NewEventDto eventDto);

    public Collection<EventShortDto> getByPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size);

    public Collection<EventShortDto> getByPrivate(Long userId, Long from, Long size);

    public Collection<EventFullDto> getByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Long from, Long size);

    public EventFullDto findById(Long userId, Long eventId);

    public EventFullDto update(Long userId, Long eventId, UpdateEventUserAndAdminRequest eventDto);
}

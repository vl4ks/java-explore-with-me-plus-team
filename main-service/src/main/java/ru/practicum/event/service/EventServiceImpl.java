package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserAndAdminRequest;
import ru.practicum.event.storage.EventRepository;

import java.util.Collection;
import java.util.List;

@Service("eventServiceImpl")
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public EventFullDto create(Long userId, NewEventDto eventDto) {
        return null;
    }

    @Override
    public Collection<EventShortDto> getByPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size) {
        return null;
    }

    @Override
    public Collection<EventShortDto> getByPrivate(Long userId, Long from, Long size) {
        return null;
    }

    @Override
    public Collection<EventFullDto> getByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Long from, Long size) {
        return null;
    }

    @Override
    public EventFullDto findById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserAndAdminRequest eventDto) {
        return null;
    }
}

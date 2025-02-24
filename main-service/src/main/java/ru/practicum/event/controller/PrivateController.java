package ru.practicum.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserAndAdminRequest;
import ru.practicum.event.service.EventService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class PrivateController {
    private final EventService eventService;

    @GetMapping
    public Collection<EventShortDto> get(
            @PathVariable Long userId,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long size
    ) {
        log.info("Пришел GET запрос /users/{}/events?from={}&size={}", userId, from, size);
        final Collection<EventShortDto> events = eventService.getByPrivate(userId, from, size);
        log.info("Отправлен ответ GET /users/{}/events?from={}&size={} с телом: {}", userId, from, size, events);
        return events;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @RequestBody @Valid NewEventDto eventDto) {
        log.info("Пришел POST запрос /users/{}/events с телом {}", userId, eventDto);
        final EventFullDto event = eventService.create(userId, eventDto);
        log.info("Отправлен ответ POST /users/{}/events с телом: {}", userId, event);
        return event;
    }

    @GetMapping("/{eventId}")
    public EventFullDto findById(@PathVariable Long userId, Long eventId) {
        log.info("Пришел GET запрос /users/{}/events/{}", userId, eventId);
        final EventFullDto event = eventService.findById(userId, eventId);
        log.info("Отправлен ответ GET /users/{}/events/{} с телом: {}", userId, eventId, event);
        return event;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody UpdateEventUserAndAdminRequest eventDto) {
        log.info("Пришел PATCH запрос /users/{}/events/{} с телом {}", userId, eventId, eventDto);
        final EventFullDto event = eventService.update(userId, eventId, eventDto);
        log.info("Отправлен ответ PATCH /users/{}/events/{} с телом: {}", userId, eventId, event);
        return event;
    }
}

package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryDtoMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventDtoMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.model.StateAction;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.mapper.LocationDtoMapper;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.LocationService;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserDtoMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("eventServiceImpl")
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final EventRepository eventRepository;
    private final EventDtoMapper eventDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final CategoryDtoMapper categoryDtoMapper;
    private final LocationDtoMapper locationDtoMapper;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto create(Long userId, NewEventDto eventDto) {
        final User user = findUserById(userId);
        final Category category = findCategoryById(eventDto.getCategory());
        final Location location = saveLocation(eventDto.getLocation());

        validateEventDate(eventDto.getEventDate());
        final Event event = eventDtoMapper.mapFromDto(eventDto, category, location, user);
        final Event createdEvent = eventRepository.save(event);

        return eventDtoMapper.mapToFullDto(
                createdEvent,
                categoryDtoMapper.mapToDto(event.getCategory()),
                locationDtoMapper.mapToDto(event.getLocation()),
                userDtoMapper.mapToShortDto(event.getInitiator()),
                0L,
                0L
            );
    }

    @Override
    public Collection<EventShortDto> findAllByPublic(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        final Collection<Event> events = eventRepository.findAllByPublic(text, categories, paid, LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter), onlyAvailable, (Pageable) PageRequest.of(from, size));
        return events.stream()
                .map(event -> eventDtoMapper.mapToShortDto(
                        event,
                        categoryDtoMapper.mapToDto(event.getCategory()),
                        userDtoMapper.mapToShortDto(event.getInitiator()),
                        null,
                        null

                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<EventShortDto> findAllByPrivate(Long userId, Integer from, Integer size) {
        final User user = findUserById(userId);
        final Collection<Event> events = eventRepository.findAllByInitiatorId(user.getId(), PageRequest.of(from, size));
        return events.stream()
                .map(event -> eventDtoMapper.mapToShortDto(
                        event,
                        categoryDtoMapper.mapToDto(event.getCategory()),
                        userDtoMapper.mapToShortDto(event.getInitiator()),
                        null,
                        null

                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<EventFullDto> findAllByAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        final Collection<Event> events = eventRepository.findAllByAdmin(users, states, categories, LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter), (Pageable) PageRequest.of(from, size));
        return events.stream()
                .map(event -> eventDtoMapper.mapToFullDto(
                        event,
                        categoryDtoMapper.mapToDto(event.getCategory()),
                        locationDtoMapper.mapToDto(event.getLocation()),
                        userDtoMapper.mapToShortDto(event.getInitiator()),
                        null,
                        null

                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public EventFullDto findById(Long userId, Long eventId, Boolean isPublic) {
        final Event event = findEventById(eventId);

        if (isPublic && !event.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        } else if (isPublic) {
            // Сервис статистики
        } else {
            findUserById(userId);
        }

        return eventDtoMapper.mapToFullDto(
                event,
                categoryDtoMapper.mapToDto(event.getCategory()),
                locationDtoMapper.mapToDto(event.getLocation()),
                userDtoMapper.mapToShortDto(event.getInitiator()),
                0L,
                0L
        );
    }

    @Override
    public EventFullDto updateByPrivate(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
        final User user = findUserById(userId);
        final Event event = findEventById(eventId);

        validateUser(event.getInitiator(), user);
        validateEventDate(eventDto.getEventDate());
        validateStatusForPrivate(event.getState(), eventDto.getStateAction());

        final Category category = findCategoryById(eventDto.getCategory());
        final Location location = saveLocation(eventDto.getLocation());
        eventDtoMapper.updateFromDto(event, eventDto, category, location);

        return eventDtoMapper.mapToFullDto(
                event,
                categoryDtoMapper.mapToDto(event.getCategory()),
                locationDtoMapper.mapToDto(event.getLocation()),
                userDtoMapper.mapToShortDto(event.getInitiator()),
                0L,
                0L
        );
    }

    @Override
    public EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequest eventDto) {
        final Event event = findEventById(eventId);

        validateEventDateForAdmin(eventDto.getEventDate(), eventDto.getStateAction());
        validateStatusForAdmin(event.getState(), eventDto.getStateAction());

        final Category category = findCategoryById(eventDto.getCategory());
        final Location location = saveLocation(eventDto.getLocation());
        eventDtoMapper.updateFromDto(event, eventDto, category, location);

        return eventDtoMapper.mapToFullDto(
                event,
                categoryDtoMapper.mapToDto(event.getCategory()),
                locationDtoMapper.mapToDto(event.getLocation()),
                userDtoMapper.mapToShortDto(event.getInitiator()),
                0L,
                0L
        );
    }

    private void validateUser(User user, User initiator) {
        if (initiator.getId().equals(user.getId())) {
            throw new NotFoundException("Trying to change information not from initiator of event");
        }
    }

    private void validateEventDate(String eventDate) {
        if (eventDate != null && LocalDateTime.parse(eventDate, formatter).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("Event date should be early than 2 hours than current moment " + eventDate + " " + LocalDateTime.parse(eventDate, formatter));
        }
    }

    private void validateEventDateForAdmin(String eventDate, StateAction stateAction) {
        if (eventDate != null && LocalDateTime.parse(eventDate, formatter).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ForbiddenException("Event date should be early than 2 hours than current moment");
        }
        if (stateAction.equals(StateAction.PUBLISH_EVENT) && eventDate != null && LocalDateTime.parse(eventDate, formatter).isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ForbiddenException("Event date should be early than 1 hours than publish moment");
        }
    }

    private void validateStatusForPrivate(State state, StateAction stateAction) {
        if (state.equals(State.PUBLISHED)) {
            throw new ForbiddenException("Can't change event not cancelled or in moderation");
        }
        switch (stateAction) {
            case null:
            case StateAction.CANCEL_REVIEW:
            case StateAction.SEND_TO_REVIEW:
                return;
            default:
                throw new ForbiddenException("Unknown state action");
        }
    }

    private void validateStatusForAdmin(State state, StateAction stateAction) {
        if (!state.equals(State.PENDING) && stateAction.equals(StateAction.PUBLISH_EVENT)) {
            throw new ForbiddenException("Can't publish not pending event");
        }
        if (!state.equals(State.PUBLISHED) && stateAction.equals(StateAction.REJECT_EVENT)) {
            throw new ForbiddenException("Can't reject already published event");
        }
        throw new ForbiddenException("Unknown state action");
    }

    private User findUserById(Long userId) {
        final UserDto userDto = userService.findById(userId);
        final User user = userDtoMapper.mapFromDto(userDto);
        return user;
    }

    private Category findCategoryById(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        final CategoryDto categoryDto = categoryService.findById(categoryId);
        final Category category = categoryDtoMapper.mapFromDto(categoryDto);
        return category;
    }

    private Location saveLocation(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        final LocationDto createdLocationDto = locationService.create(locationDto);
        final Location location = locationDtoMapper.mapFromDto(createdLocationDto);
        return location;
    }

    private Event findEventById(Long eventId) {
        final Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Event with id=" + eventId + " was not found")
        );

        return event;
    }
}

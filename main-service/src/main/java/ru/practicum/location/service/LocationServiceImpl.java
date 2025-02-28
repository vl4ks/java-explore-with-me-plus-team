package ru.practicum.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.mapper.LocationDtoMapper;
import ru.practicum.location.model.Location;
import ru.practicum.location.storage.LocationRepository;

@Slf4j
@Service("locationServiceImpl")
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDtoMapper locationDtoMapper;

    @Override
    public LocationDto create(LocationDto locationDto) {
        final Location location = locationDtoMapper.mapFromDto(locationDto);
        final Location createdLocation = locationRepository.save(location);
        return locationDtoMapper.mapToDto(createdLocation);
    }
}

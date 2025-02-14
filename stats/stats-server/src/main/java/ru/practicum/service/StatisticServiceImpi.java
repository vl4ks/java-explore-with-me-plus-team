package ru.practicum.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.CreateHitDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResponseStatsDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.reposirory.StatisticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpi implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public ResponseHitDto create(CreateHitDto createHitDto) {
        final Hit hit = HitMapper.mapToHit(createHitDto);
        final Hit createdHit = statisticRepository.save(hit);
        return HitMapper.mapToResponseDto(createdHit);
    }

    @Override
    public List<ResponseStatsDto> get(CreateStatsDto createStatsDto) {
        if (createStatsDto.getStart() == null || createStatsDto.getEnd() == null) {
            throw new ValidationException("You need to chose start and end dates.");
        }
        final List<StatisticRepository.ResponseStatsDto> hits;
        if (createStatsDto.getUris().isEmpty()) {
            hits = statisticRepository.getByAllUris(createStatsDto.getStart(), createStatsDto.getEnd(), createStatsDto.getUris());
        } else {
            hits = statisticRepository.getByUris(createStatsDto.getStart(), createStatsDto.getEnd(), createStatsDto.getUris());
        }
        return hits.stream()
                .map(stats -> ResponseStatsDto.builder()
                        .app(stats.getApp())
                        .uri(stats.getUri())
                        .hits(createStatsDto.getUnique() ? stats.getUniqHits() : stats.getHits())
                        .build()
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

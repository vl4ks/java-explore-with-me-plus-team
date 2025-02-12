package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.CreateHitDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResonseStatsDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.reposirory.StatisticRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpi implements StatisticService {

    private StatisticRepository statisticRepository;

    @Override
    public ResponseHitDto create(CreateHitDto createHitDto) {
        return null;
    }

    @Override
    public List<ResonseStatsDto> get(CreateStatsDto createStatsDto) {
        return List.of();
    }
}

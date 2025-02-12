package ru.practicum.service;

import ru.practicum.CreateHitDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResonseStatsDto;

import java.util.List;

public interface StatisticService {

    ResponseHitDto create(CreateHitDto createHitDto);
    List<ResonseStatsDto> get(CreateStatsDto createStatsDto);
}
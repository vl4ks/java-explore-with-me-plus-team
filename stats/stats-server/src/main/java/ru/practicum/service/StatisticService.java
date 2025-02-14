package ru.practicum.service;

import ru.practicum.CreateHitDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.ResponseStatsDto;

import java.util.List;

public interface StatisticService {
    ResponseHitDto create(CreateHitDto createHitDto);

    List<ResponseStatsDto> get(CreateStatsDto createStatsDto);
}
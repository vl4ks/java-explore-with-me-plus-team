package ru.practicum.controller;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ResponseStatsDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.CreateHitDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.service.StatisticService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {

    private final StatisticService statisticService;

    @GetMapping("/stats")
    public List<ResponseStatsDto> getStats(@RequestBody @Valid CreateStatsDto createStatsDto) {
        log.info("Получен запрос на получение статистики по посещениям");
        return statisticService.get(createStatsDto);
    }

    @PostMapping("/hit")
    public ResponseHitDto createHit(@RequestBody @Valid CreateHitDto createHitDto) {
        log.info("Сохранение информации об обращении к эндпоинту");
        return statisticService.create(createHitDto);
    }
}
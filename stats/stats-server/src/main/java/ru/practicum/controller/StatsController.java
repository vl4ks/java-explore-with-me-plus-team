package ru.practicum.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ResonseStatsDto;
import ru.practicum.CreateStatsDto;
import ru.practicum.ResponseHitDto;
import ru.practicum.CreateHitDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.service.StatisticService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {

    private final StatisticService statisticService;

    @GetMapping("/stats")
    public List<ResonseStatsDto> getStats(@RequestBody CreateStatsDto createStatsDto) {
        log.info("Получен запрос на получение статистики по посещениям");
        return statisticService.get(createStatsDto);
    }

    @PostMapping("/hit")
    public ResponseHitDto createHit(@RequestBody CreateHitDto createHitDto) {
        log.info("Сохранение информации об обращении к эндпоинту");
        return statisticService.create(createHitDto);
    }
}
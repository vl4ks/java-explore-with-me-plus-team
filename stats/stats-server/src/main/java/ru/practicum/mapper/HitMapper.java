package ru.practicum.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.CreateHitDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitMapper {

    public static Hit mapToHit(CreateHitDto createHitDto) {
        return Hit.builder()
                .app(createHitDto.getApp())
                .uri(createHitDto.getUri())
                .ip(createHitDto.getIp())
                .timestamp(LocalDateTime.parse(createHitDto.getTimestamp())).build();
    }
}

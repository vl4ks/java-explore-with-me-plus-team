package ru.practicum.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.CreateHitDto;
import ru.practicum.model.Hit;

import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Hit, Long> {
}

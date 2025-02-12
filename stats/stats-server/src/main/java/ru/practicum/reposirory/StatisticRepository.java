package ru.practicum.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Hit;

public interface StatisticRepository extends JpaRepository<Hit, Long> {
}

package ru.practicum.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Hit, Long> {
    @Query(value = """
            SELECT
                app,
                uri,
                COUNT(*) AS hits,
                COUNT(DISTINCT ip) AS uniqHits
            FROM hit
            WHERE timestamp >= ?1 ::timestamp
                AND timestamp < ?2 ::timestamp
                AND uri IN ?3
            GROUP BY 1, 2
            ORDER BY hits DESC
        """,
        nativeQuery = true
    )
    List<ResponseStatsDto> getByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = """
            SELECT
                app,
                uri,
                COUNT(*) AS hits,
                COUNT(DISTINCT ip) AS uniqHits
            FROM hit
            WHERE timestamp >= ?1 ::timestamp
                AND timestamp < ?2 ::timestamp
            GROUP BY 1, 2
            ORDER BY hits DESC
        """,
            nativeQuery = true
    )
    List<ResponseStatsDto> getByAllUris(LocalDateTime start, LocalDateTime end);

    interface ResponseStatsDto {
        String getApp();

        String getUri();

        Long getHits();

        Long getUniqHits();
    }
}

package ru.practicum;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatsClient extends BaseClient {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${server.application.name:ewm-main-service}")
    private String appName;

    @Value("${server.application.url}")
    private String serverUrl;

    public StatsClient(String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> saveHit(HttpServletRequest request) {
        final ResponseHitDto hitDto = ResponseHitDto.builder()
                .app(appName)
                .url(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        return post(hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String uriBuilder = "/stats?start={start}&end={end}";
        Map<String, Object> params = new HashMap<>();
        params.put("start", start.format(FORMATTER));
        params.put("end", end.format(FORMATTER));
        if (uris != null && !uris.isEmpty()) {
            params.put("uris", String.join(",", uris));
        }
        params.put("unique", true);

        return get(uriBuilder, params);
    }
}

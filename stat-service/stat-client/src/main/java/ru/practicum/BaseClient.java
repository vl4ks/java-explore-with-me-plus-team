package ru.practicum;

import org.springframework.web.client.RestClient;

public class BaseClient {
    protected final RestClient restClient;

    public BaseClient(RestClient restClient) {
        this.restClient = restClient;
    }
}

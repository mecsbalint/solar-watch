package com.mecsbalint.solarwatch.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Fetcher {
    private final WebClient webClient;

    public Fetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T fetch(String url, Class<T> returnClass) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(returnClass)
                .block();
    }
}

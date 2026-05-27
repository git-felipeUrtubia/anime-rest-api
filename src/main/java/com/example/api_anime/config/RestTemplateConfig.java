package com.example.api_anime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
        // Aquí también podrías configurar timeouts, interceptores, etc.
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://video.bunnycdn.com")
                .build();
    }
}

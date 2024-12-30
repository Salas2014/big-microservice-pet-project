package com.salas.customerapp.config;

import com.salas.customerapp.service.WebClientProductsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClientProductsClient webClientProductsClient(
            @Value("${services.catalogue.uri:http://localhost:8081}") String url
    ) {
        return new WebClientProductsClient(WebClient.builder()
                .baseUrl(url)
                .build());
    }
}

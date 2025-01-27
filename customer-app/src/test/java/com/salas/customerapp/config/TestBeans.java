package com.salas.customerapp.config;

import com.salas.customerapp.client.WebClientFavouriteProductsClient;
import com.salas.customerapp.client.WebClientProductReviewsClient;
import com.salas.customerapp.service.WebClientProductsClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestBeans {


    @Bean
    @Primary
    public WebClientProductsClient mockWebClientProductsClient() {
        return new WebClientProductsClient(
                WebClient.builder()
                        .baseUrl("http://localhost:54321")
                        .build()
        );
    }

    @Bean
    @Primary
    public WebClientFavouriteProductsClient mockWebClientFavouriteProductsClient() {
        return new WebClientFavouriteProductsClient(WebClient.builder()
                .baseUrl("http://localhost:54321")
                .build());
    }

    @Bean
    @Primary
    public WebClientProductReviewsClient mockWebClientProductReviewsClient() {
        return new WebClientProductReviewsClient(WebClient.builder()
                .baseUrl("http://localhost:54321")
                .build());
    }
}

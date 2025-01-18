package com.salas.customerapp.config;

import com.salas.customerapp.client.WebClientFavouriteProductsClient;
import com.salas.customerapp.client.WebClientProductReviewsClient;
import com.salas.customerapp.service.WebClientProductsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {


    @Bean
    public WebClientProductsClient webClientProductsClient(
            @Value("${services.catalogue.uri:http://localhost:8081}") String url,
            WebClient.Builder selmagServicesWebClientBuilder) {
        return new WebClientProductsClient(selmagServicesWebClientBuilder
                .baseUrl(url)
                .build());
    }

    @Bean
    public WebClientFavouriteProductsClient webClientFavouriteProductsClient(
            @Value("${services.feedback.uri:http://localhost:8084}") String url,
            WebClient.Builder selmagServicesWebClientBuilder) {
        return new WebClientFavouriteProductsClient(selmagServicesWebClientBuilder
                .baseUrl(url)
                .build());
    }

    @Bean
    public WebClientProductReviewsClient webClientProductReviewsClient(
            @Value("${services.feedback.uri:http://localhost:8084}") String url,
            WebClient.Builder selmagServicesWebClientBuilder) {
        return new WebClientProductReviewsClient(selmagServicesWebClientBuilder
                .baseUrl(url)
                .build());
    }
}

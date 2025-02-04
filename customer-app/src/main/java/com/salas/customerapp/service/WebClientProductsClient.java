package com.salas.customerapp.service;

import com.salas.customerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class WebClientProductsClient implements ProductsClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findProducts(String filter) {
        return webClient.get()
                .uri("catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @Override
    public Mono<Product> findProductById(int id) {
        return webClient.get()
                .uri("/catalogue-api/products/{productId}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorComplete(NoSuchElementException.class);
    }
}

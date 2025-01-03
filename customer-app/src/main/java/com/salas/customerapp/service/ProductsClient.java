package com.salas.customerapp.service;

import com.salas.customerapp.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsClient {

    Flux<Product> findProducts(String filter);

    Mono<Product> findProductById(int id);
}

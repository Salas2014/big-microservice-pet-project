package com.salas.customerapp.service;

import com.salas.customerapp.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {

    Flux<Product> findProducts(String filter);
}

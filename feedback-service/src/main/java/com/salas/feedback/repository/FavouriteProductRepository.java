package com.salas.feedback.repository;

import com.salas.feedback.entity.FavouriteProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FavouriteProductRepository extends ReactiveCrudRepository<FavouriteProduct, UUID> {

    Flux<FavouriteProduct> findAllByUserId(String userId);

    Mono<FavouriteProduct> findByProductId(int productId);

    Mono<Void> deleteByProductIdAndUserId(int productId);
}

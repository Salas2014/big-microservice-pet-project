package com.salas.feedback.service;

import com.salas.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsService {

    Mono<FavouriteProduct> addFavouriteProduct(int productId);

    Mono<Void> deleteFavouriteProduct(int productId);

    Mono<FavouriteProduct> findFavouritesProductByProduct(int productId);

    Flux<FavouriteProduct> findAll();
}

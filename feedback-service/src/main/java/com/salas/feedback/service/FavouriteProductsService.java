package com.salas.feedback.service;

import com.salas.feedback.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsService {

    Mono<FavouriteProduct> addFavouriteProduct(int productId, String userId);

    Mono<Void> deleteFavouriteProduct(int productId, String userId);

    Mono<FavouriteProduct> findFavouritesProductByProduct(int productId, String userId);

    Flux<FavouriteProduct> findAllFavouritesProducts(String userId);
}

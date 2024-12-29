package com.salas.customerapp.service;

import com.salas.customerapp.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsService {

    Mono<FavouriteProduct> addFavouriteProduct(int productId);

    Mono<Void> deleteFavouriteProduct(int productId);

    Mono<FavouriteProduct> findFavouritesProductByProduct(int productId);

    Flux<FavouriteProduct> findAllFavouritesProducts();
}

package com.salas.customerapp.client;

import com.salas.customerapp.entity.FavouriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouriteProductsClient {
    Mono<FavouriteProduct> findFavouritesProductByProduct(int productId);

    Mono<FavouriteProduct> addFavouriteProduct(Integer productId);

    Mono<Void> deleteFavouriteProduct(Integer productId);

    Flux<FavouriteProduct> findAllFavouritesProducts();

}

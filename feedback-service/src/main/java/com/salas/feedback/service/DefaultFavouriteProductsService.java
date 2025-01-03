package com.salas.feedback.service;

import com.salas.feedback.entity.FavouriteProduct;
import com.salas.feedback.repository.FavouriteProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
public class DefaultFavouriteProductsService implements FavouriteProductsService {

    private final FavouriteProductRepository favouriteProductRepository;

    public DefaultFavouriteProductsService(FavouriteProductRepository favouriteProductRepository) {
        this.favouriteProductRepository = favouriteProductRepository;
    }

    @Override
    public Mono<FavouriteProduct> addFavouriteProduct(int productId, String userId) {
        return favouriteProductRepository.save(new FavouriteProduct(UUID.randomUUID(), productId, userId));
    }

    @Override
    public Mono<Void> deleteFavouriteProduct(int productId, String userId) {
        return favouriteProductRepository.deleteByProductIdAndUserId(productId, userId);
    }

    @Override
    public Mono<FavouriteProduct> findFavouritesProductByProduct(int productId, String userId) {
        return favouriteProductRepository.findByProductIdAndUserId(productId, userId);
    }

    @Override
    public Flux<FavouriteProduct> findAllFavouritesProducts(String userId) {
        return favouriteProductRepository.findAllByUserId(userId);
    }
}

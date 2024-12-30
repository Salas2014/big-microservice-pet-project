package com.salas.feedback.service;

import com.salas.feedback.entity.FavouriteProduct;
import com.salas.feedback.repository.FavouriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavouriteProductsService implements FavouriteProductsService {

    private final FavouriteProductRepository favouriteProductRepository;

    @Override
    public Mono<FavouriteProduct> addFavouriteProduct(int productId) {
        return favouriteProductRepository.save(new FavouriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> deleteFavouriteProduct(int productId) {
        return favouriteProductRepository.deleteByProductId(productId);
    }

    @Override
    public Mono<FavouriteProduct> findFavouritesProductByProduct(int productId) {
        return favouriteProductRepository.findByProductId(productId);
    }

    @Override
    public Flux<FavouriteProduct> findAllFavouritesProducts() {
        return favouriteProductRepository.findAll();
    }
}

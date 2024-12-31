package com.salas.customerapp.client;

import com.salas.customerapp.client.exception.ClientBadRequestException;
import com.salas.customerapp.client.payload.NewFavouriteProductPayload;
import com.salas.customerapp.entity.FavouriteProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class WebClientFavouriteProductsClient implements FavouriteProductsClient {

    private final WebClient webClient;

    @Override
    public Mono<FavouriteProduct> findFavouritesProductByProduct(int productId) {
        return webClient.get()
                .uri("feedback-api/favourite-products/by-product-id/{productId}", productId)
                .retrieve()
                .bodyToMono(FavouriteProduct.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouriteProduct> addFavouriteProduct(Integer productId) {
        return webClient.post()
                .uri("feedback-api/favourite-products")
                .bodyValue(new NewFavouriteProductPayload(productId))
                .retrieve()
                .bodyToMono(FavouriteProduct.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) Objects.requireNonNull(Objects.requireNonNull(exception.getResponseBodyAs(ProblemDetail.class))
                                        .getProperties()).get("errors"))));
    }

    @Override
    public Mono<Void> deleteFavouriteProduct(Integer productId) {
        return webClient.delete()
                .uri("feedback-api/favourite-products/by-product-id/{productId}", productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    @Override
    public Flux<FavouriteProduct> findAllFavouritesProducts() {
        return webClient.get()
                .uri("feedback-api/favourite-products")
                .retrieve()
                .bodyToFlux(FavouriteProduct.class);
    }
}

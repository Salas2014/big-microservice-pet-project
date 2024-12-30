package com.salas.feedback.controller;

import com.salas.feedback.controller.payload.NewFavouriteProductPayload;
import com.salas.feedback.entity.FavouriteProduct;
import com.salas.feedback.service.FavouriteProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/favourite-products")
@RequiredArgsConstructor
public class FavouriteProductsRestController {

    private final FavouriteProductsService service;

    @GetMapping
    public Flux<FavouriteProduct> findAllFavoriteProducts() {
        return service.findAllFavouritesProducts();
    }

    @GetMapping("/by-product-id/{productId:\\d+}")
    public Mono<FavouriteProduct> findFavouriteProductByProductId(@PathVariable("productId") int productId) {
        return service.findFavouritesProductByProduct(productId);
    }

    @PostMapping()
    public Mono<ResponseEntity<FavouriteProduct>> addProductToFavourite(
            @Valid @RequestBody Mono<NewFavouriteProductPayload> payloadMono, UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> service.addFavouriteProduct(payload.productId()))
                .map(favouriteProduct -> ResponseEntity.created(
                                uriComponentsBuilder.replacePath("/feedback-api/favourite-products/{id}").build(favouriteProduct.getUuid()))
                        .body(favouriteProduct));
    }

    @DeleteMapping("/by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavourite(@PathVariable("productId") int productId) {
        return service.deleteFavouriteProduct(productId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}

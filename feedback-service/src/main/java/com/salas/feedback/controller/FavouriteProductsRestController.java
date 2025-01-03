package com.salas.feedback.controller;

import com.salas.feedback.controller.payload.NewFavouriteProductPayload;
import com.salas.feedback.entity.FavouriteProduct;
import com.salas.feedback.service.FavouriteProductsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/favourite-products")
public class FavouriteProductsRestController {

    private final FavouriteProductsService service;

    public FavouriteProductsRestController(FavouriteProductsService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<FavouriteProduct> findAllFavoriteProducts( Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono.flatMapMany(token ->
                service.findAllFavouritesProducts(token.getToken().getSubject()));
    }

    @GetMapping("/by-product-id/{productId:\\d+}")
    public Mono<FavouriteProduct> findFavouriteProductByProductId(@PathVariable("productId") int productId,
                                                                  Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono
                .flatMap(token -> service.findFavouritesProductByProduct(productId, token.getToken().getSubject()));
    }

    @PostMapping()
    public Mono<ResponseEntity<FavouriteProduct>> addProductToFavourite(
            @Valid @RequestBody Mono<NewFavouriteProductPayload> payloadMono, UriComponentsBuilder uriComponentsBuilder,
            Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return Mono.zip(authenticationTokenMono, payloadMono)
                .flatMap(tuple -> service.addFavouriteProduct(tuple.getT2().productId(), tuple.getT1().getToken().getSubject()))
                .map(favouriteProduct -> ResponseEntity.created(
                                uriComponentsBuilder.replacePath("/feedback-api/favourite-products/{id}").build(favouriteProduct.getUuid()))
                        .body(favouriteProduct));
    }

    @DeleteMapping("/by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavourite(@PathVariable("productId") int productId,
                                                                 Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono.flatMap(token -> service.deleteFavouriteProduct(productId, token.getToken().getSubject())
                .thenReturn(ResponseEntity.noContent().build()));
    }
}

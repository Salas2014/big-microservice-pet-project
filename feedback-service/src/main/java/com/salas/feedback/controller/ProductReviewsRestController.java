package com.salas.feedback.controller;

import com.salas.feedback.controller.payload.NewProductReviewPayload;
import com.salas.feedback.entity.ProductReview;
import com.salas.feedback.service.ProductReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/product-reviews")
public class ProductReviewsRestController {

    private final ProductReviewService service;

    public ProductReviewsRestController(ProductReviewService service) {
        this.service = service;
    }

    @GetMapping("/by-product-id/{productId:\\d+}")
    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable("productId") int productId) {
        return service.findReviewsByProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createProductReview(
            @Valid @RequestBody Mono<NewProductReviewPayload> payloadMono, UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> service.createProductReview(
                        payload.productId(), payload.rating(), payload.review()))
                .map(productReview -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("feedback-api/product-reviews/{id}")
                                .build(productReview.getId()))
                        .body(productReview));
    }
}

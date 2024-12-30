package com.salas.feedback.service;

import com.salas.feedback.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewService {

    Flux<ProductReview> findReviewsByProductId(int productId);

    Mono<ProductReview> createProductReview(int productId, int rating, String review);
}

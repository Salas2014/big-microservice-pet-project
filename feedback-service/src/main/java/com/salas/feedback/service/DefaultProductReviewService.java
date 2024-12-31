package com.salas.feedback.service;

import com.salas.feedback.entity.ProductReview;
import com.salas.feedback.repository.ProductReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class DefaultProductReviewService implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;

    public DefaultProductReviewService(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    public Mono<ProductReview> createProductReview(int productId, int rating, String review) {
        return productReviewRepository.save(new ProductReview(UUID.randomUUID(), productId, rating, review));
    }

    @Override
    public Flux<ProductReview> findReviewsByProductId(int productId) {
        return productReviewRepository.find(productId);
    }
}

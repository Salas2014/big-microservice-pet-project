package com.salas.feedback.repository;

import com.salas.feedback.entity.ProductReview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductReviewRepository extends ReactiveCrudRepository<ProductReview, UUID> {


    Flux<ProductReview> findAllByProductId(int productId);
}

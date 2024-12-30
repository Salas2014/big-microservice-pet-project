package com.salas.feedback.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductReview {
    private UUID id;
    private int productId;
    private int rating;
    private String review;

    public ProductReview(UUID id, int productId, int rating, String review) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.review = review;
    }
}

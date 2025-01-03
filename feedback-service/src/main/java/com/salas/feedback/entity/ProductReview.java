package com.salas.feedback.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@Document(value = "product_review")
public class ProductReview {
    @Id
    private UUID id;
    private int productId;
    private int rating;
    private String review;
    private String userId;

    public ProductReview(UUID id, int productId, int rating, String review, String userId) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public UUID getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}

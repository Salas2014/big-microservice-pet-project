package com.salas.feedback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("favourite_product")
public class FavouriteProduct {
    @Id
    private UUID uuid;
    private int productId;
    private String userId;

    public FavouriteProduct() {
    }

    public FavouriteProduct(UUID uuid, int productId) {
        this.uuid = uuid;
        this.productId = productId;
    }

    public FavouriteProduct(UUID uuid, int productId, String userId) {
        this.uuid = uuid;
        this.productId = productId;
        this.userId = userId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }
}


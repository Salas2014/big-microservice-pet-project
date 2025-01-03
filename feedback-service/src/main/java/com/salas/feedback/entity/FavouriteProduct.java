package com.salas.feedback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class FavouriteProduct {
    @Id
    private UUID uuid;
    private int productId;

    public FavouriteProduct() {
    }

    public FavouriteProduct(UUID uuid, int productId) {
        this.uuid = uuid;
        this.productId = productId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getProductId() {
        return productId;
    }
}


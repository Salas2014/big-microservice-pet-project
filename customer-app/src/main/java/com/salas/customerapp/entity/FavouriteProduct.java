package com.salas.customerapp.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class FavouriteProduct {
    private UUID uuid;
    private int productId;

    public FavouriteProduct() {
    }

    public FavouriteProduct(UUID uuid, int productId) {
        this.uuid = uuid;
        this.productId = productId;
    }
}


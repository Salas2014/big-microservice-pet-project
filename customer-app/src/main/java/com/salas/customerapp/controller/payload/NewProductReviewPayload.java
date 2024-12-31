package com.salas.customerapp.controller.payload;

public record NewProductReviewPayload(int productId, Integer rating, String review) {
}

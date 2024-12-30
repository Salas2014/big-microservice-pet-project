package com.salas.feedback.controller.payload;

public record NewProductReviewPayload(Integer productId, Integer rating, String review) {
}

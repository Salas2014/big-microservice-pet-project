package com.salas.customerapp.client.payload;

record NewProductReviewPayload(Integer productId, Integer rating, String review) {}
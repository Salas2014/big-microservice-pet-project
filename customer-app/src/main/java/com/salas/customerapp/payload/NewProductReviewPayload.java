package com.salas.customerapp.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductReviewPayload(
        @NotNull(message = "{customer.products.review.create.errors.rating_is_null}")
        @Min(value = 1, message = "{customer.products.review.create.errors.rating_is_below_min}")
        @Max(value = 5, message = "{customer.products.review.create.errors.rating_is_bellow_max}")
        Integer rating,

        @Size(max = 1000, message = "{customer.products.review.create.errors.rating_is_too_big}")
        String review) {
}

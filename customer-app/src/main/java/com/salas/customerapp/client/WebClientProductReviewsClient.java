package com.salas.customerapp.client;

import com.salas.customerapp.client.exception.ClientBadRequestException;
import com.salas.customerapp.controller.payload.NewProductReviewPayload;
import com.salas.customerapp.entity.ProductReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
public class WebClientProductReviewsClient implements ProductReviewsClient {
    private final WebClient webClient;

    @Override
    public Flux<ProductReview> findProductReviewsByProductId(Integer productId) {
        return webClient.get()
                .uri("feedback-api/product-reviews/by-product-id/{productId}", productId)
                .retrieve()
                .bodyToFlux(ProductReview.class);
    }

    @Override
    public Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review) {

        return webClient.post()
                .uri("feedback-api/product-reviews")
                .bodyValue(new NewProductReviewPayload(productId, rating, review))
                .retrieve()
                .bodyToMono(ProductReview.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) Objects.requireNonNull(Objects.requireNonNull(exception.getResponseBodyAs(ProblemDetail.class))
                                        .getProperties()).get("errors"))));
    }

}


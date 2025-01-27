package com.salas.feedback.controller;

import com.salas.feedback.entity.ProductReview;
import com.salas.feedbackservice.config.TestBeans;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(TestBeans.class)
class ProductReviewsRestControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(ProductReviewsRestControllerIT.class);

    @BeforeEach
    void setUp() {
        reactiveMongoTemplate.insertAll(List.of(
                new ProductReview(UUID.fromString("25d57ed0-7b3e-4d72-a8a9-d2ba2b0d1f56"), 1, 1, "review-1", "user-1"),
                new ProductReview(UUID.fromString("f5dcf604-3c89-4a4a-b2e6-78d4e1f8a9e2"), 1, 4, "review-2", "user-2"),
                new ProductReview(UUID.fromString("2b1b8ea4-8e24-4c3a-9ed9-9131a2bc7e65"), 1, 3, "review-3", "user-3")
        )).blockLast();
    }

    @AfterEach
    void tearDown() {
        reactiveMongoTemplate.remove(ProductReview.class).all().block();
    }

    @Test
    void findProductReviewsByProductId() {

        webTestClient
                .mutate().filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    log.info("===== Start Request =====");
                    log.info("{} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers().forEach((header, value) -> log.info("{} {}", header, value));
                    log.info("==== End Request ====");
                    return Mono.just(clientRequest);
                }))
                .build().get()
                .uri("/feedback-api/product-reviews/by-product-id/1")
                .exchange()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody().json("""
                        [
                          {"id":  "25d57ed0-7b3e-4d72-a8a9-d2ba2b0d1f56", "productId": 1, "rating": 1, "review":  "review-1", "userId": "user-1"},
                          {"id":  "f5dcf604-3c89-4a4a-b2e6-78d4e1f8a9e2", "productId": 1, "rating": 4, "review":  "review-2", "userId": "user-2"},
                          {"id":  "2b1b8ea4-8e24-4c3a-9ed9-9131a2bc7e65", "productId": 1, "rating": 3, "review":  "review-3", "userId": "user-3"}
                        ]
                        """);
    }

    @Test
    void createProductReview_RequestIsValid_ReturnsCreatedProductReview() {

        webTestClient

                .post()
                .uri("/feedback-api/product-reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "productId": 1,
                          "rating": 2,
                          "review": "good"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody().json("""
                         {
                                                  "productId": 1,
                                                  "rating": 2,
                                                  "review": "good",
                                                  "userId": "user-tester"
                                                }
                        """).jsonPath("$.id").exists();
    }

    @Test
    void createProductReview_RequestIsNotValid() {

        webTestClient
                .post()
                .uri("/feedback-api/product-reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "productId": null,
                          "rating": -12,
                          "review": "good"
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().doesNotExist(HttpHeaders.LOCATION)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody().json("""
                         {
                         "errors": [
                         "product id not be empty",
                         "Rating must be less than 1"
                         ]
                                                }
                        """);
    }

}
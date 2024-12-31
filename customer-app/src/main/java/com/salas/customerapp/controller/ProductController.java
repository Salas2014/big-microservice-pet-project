package com.salas.customerapp.controller;

import com.salas.customerapp.client.FavouriteProductsClient;
import com.salas.customerapp.client.ProductReviewsClient;
import com.salas.customerapp.client.exception.ClientBadRequestException;
import com.salas.customerapp.controller.payload.NewProductReviewPayload;
import com.salas.customerapp.entity.Product;
import com.salas.customerapp.service.ProductsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
@Slf4j
public class ProductController {

    private final ProductsClient productsClient;
    private final FavouriteProductsClient favouriteProductsClient;
    private final ProductReviewsClient productReviewsClient;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> findProduct(@PathVariable("productId") int id) {
        return productsClient.findProductBhyId(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.product.error.not_found")));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int id, Model model) {
        model.addAttribute("isFavourite", false);
        return productReviewsClient.findProductReviewsByProductId(id)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(
                        favouriteProductsClient.findFavouritesProductByProduct(id)
                                .doOnNext(favouriteProduct -> model.addAttribute("isFavourite", true))
                )
                .thenReturn("customer/products/product");
    }

    @PostMapping("/add_to_favourites")
    public Mono<String> addToFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favouriteProductsClient.addFavouriteProduct(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                        .onErrorResume(ex -> {
                            log.error(ex.getMessage(), ex);
                            return Mono.just("redirect:/customer/products/%d".formatted(productId));
                        }));
    }

    @PostMapping("/delete_from_favourites")
    public Mono<String> deleteFromFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favouriteProductsClient.deleteFavouriteProduct(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("/create_review")
    public Mono<String> createReview(@PathVariable("productId") int productId, NewProductReviewPayload payload,
                                     Model model) {
        return productReviewsClient.createProductReview(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/customer/products/%d".formatted(productId))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("isFavourite", false);
                    model.addAttribute("payload", payload);
                    model.addAttribute("errors", exception.getErrors());
                    return favouriteProductsClient.findFavouritesProductByProduct(productId)
                            .doOnNext(favouriteProduct -> model.addAttribute("isFavourite", true))
                            .thenReturn("customer/products/product");
                });

    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(Model model, NoSuchElementException exception) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }

}

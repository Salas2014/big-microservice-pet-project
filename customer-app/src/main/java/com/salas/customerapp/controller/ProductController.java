package com.salas.customerapp.controller;

import com.salas.customerapp.entity.Product;
import com.salas.customerapp.payload.NewProductReviewPayload;
import com.salas.customerapp.service.FavouriteProductsService;
import com.salas.customerapp.service.ProductReviewService;
import com.salas.customerapp.service.ProductsClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;
    private final FavouriteProductsService favouriteProductsService;
    private final ProductReviewService productReviewService;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> findProduct(@PathVariable("productId") int id) {
        return productsClient.findProductBhyId(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.product.error.not_found")));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int id, Model model) {
        model.addAttribute("isFavourite", false);
        return productReviewService.findReviewsByProductId(id)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(
                        favouriteProductsService.findFavouritesProductByProduct(id)
                                .doOnNext(favouriteProduct -> model.addAttribute("isFavourite", true))
                )
                .thenReturn("customer/products/product");
    }

    @PostMapping("/add_to_favourites")
    public Mono<String> addToFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favouriteProductsService.addFavouriteProduct(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("/delete_from_favourites")
    public Mono<String> deleteFromFavourites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favouriteProductsService.deleteFavouriteProduct(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId)));
    }

    @PostMapping("/create_review")
    public Mono<String> createReview(@PathVariable("productId") int productId, @Valid NewProductReviewPayload payload,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isFavourite", false);
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return favouriteProductsService.findFavouritesProductByProduct(productId)
                    .doOnNext(favouriteProduct -> model.addAttribute("isFavourite", true))
                    .thenReturn("customer/products/product");
        } else {
            return productReviewService.createProductReview(productId, payload.rating(), payload.review())
                    .thenReturn("redirect:/customer/products/%d".formatted(productId));

        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(Model model, NoSuchElementException exception) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}

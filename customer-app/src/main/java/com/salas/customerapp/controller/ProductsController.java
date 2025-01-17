package com.salas.customerapp.controller;

import com.salas.customerapp.client.FavouriteProductsClient;
import com.salas.customerapp.entity.FavouriteProduct;
import com.salas.customerapp.service.ProductsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer/products")
public class ProductsController {

    private final ProductsClient productsClient;
    private final FavouriteProductsClient favouriteProductsClient;

    @GetMapping("/list")
    public Mono<String> getProductsListPage(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return productsClient.findProducts(filter)
                .collectList()
                .doOnNext(products -> model.addAttribute("products", products))
                .thenReturn("customer/products/list");
    }

    @GetMapping("/favourites")
    public Mono<String> getFavouritesPage(Model model, @RequestParam(name = "filter", required = false) String filter) {
        return favouriteProductsClient.findAllFavouritesProducts()
                .map(FavouriteProduct::productId)
                .collectList()
                .flatMap(favouriteProducts -> productsClient.findProducts(filter).filter(
                                product -> favouriteProducts.contains(product.id()))
                        .collectList()
                        .doOnNext(products -> model.addAttribute("products", products))
                )
                .thenReturn("customer/products/favourites");
    }
}

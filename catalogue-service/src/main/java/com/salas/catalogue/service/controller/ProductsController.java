package com.salas.catalogue.service.controller;

import com.salas.catalogue.service.controller.payload.NewProductPayload;
import com.salas.catalogue.service.entity.Product;
import com.salas.catalogue.service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("catalogue-api/products")
public class ProductsController {

    @Autowired
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Iterable<Product> findAll(@RequestParam(name = "filter", required = false) String filter) {
        return productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody NewProductPayload payload, BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }

        } else {
            Product product = productService.createProduct(payload.title(), payload.details());
            URI uri = uriComponentsBuilder.replacePath("/catalogue/products/{productId}").build(
                    Map.of("productId", product.getId()));
            return ResponseEntity
                    .created(uri)
                    .body(product);
        }
    }
}

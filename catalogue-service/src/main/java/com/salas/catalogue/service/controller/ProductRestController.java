package com.salas.catalogue.service.controller;

import com.salas.catalogue.service.controller.payload.UpdateProductPayload;
import com.salas.catalogue.service.entity.Product;
import com.salas.catalogue.service.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("catalogue-api/products/{productId:\\d+}")
public class ProductRestController {

    @Autowired
    private final ProductService productService;
    @Autowired
    private final MessageSource messageSource;

    public ProductRestController(ProductService productService, MessageSource messageSource) {
        this.productService = productService;
        this.messageSource = messageSource;
    }

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") int productId) {
        return productService.findProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("product not fond with id - %d".formatted(productId)));
    }

    @GetMapping()
    public Product findProductById(@ModelAttribute("product") Product product) {
        return product;
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                           @Valid @RequestBody UpdateProductPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }

        } else {
            this.productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        this.productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}

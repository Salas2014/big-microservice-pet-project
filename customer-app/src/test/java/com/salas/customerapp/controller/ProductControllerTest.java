package com.salas.customerapp.controller;

import com.salas.customerapp.client.FavouriteProductsClient;
import com.salas.customerapp.client.ProductReviewsClient;
import com.salas.customerapp.entity.Product;
import com.salas.customerapp.service.ProductsClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock ProductsClient productsClient;
    @Mock FavouriteProductsClient favouriteProductsClient;
    @Mock ProductReviewsClient productReviewsClient;

    @InjectMocks
    ProductController productController;


    @Test
    void handleNoSuchElementException() {
        var exception = new NoSuchElementException("Not found");
        var model = new ConcurrentModel();

        var result = productController.handleNoSuchElementException(model, exception);

        assertEquals("errors/404", result);
        assertEquals("Not found", model.getAttribute("error"));
    }

    @Test
    void findProduct_ProductExists_ReturnNotEmptyMono() {
        var product = new Product(1, "First", "Description First");
        doReturn(Mono.just(product)).when(productsClient).findProductById(1);

        StepVerifier.create(productController.findProduct(1))
                .expectNext(new Product(1, "First", "Description First"))
                .verifyComplete();

        verify(productsClient).findProductById(1);
        verifyNoMoreInteractions(productsClient);
    }

    @Test
    void findProduct_ProductDoesNotExist_ReturnError() {
        doReturn(Mono.empty()).when(productsClient).findProductById(1);

        StepVerifier.create(productController.findProduct(1))
                .expectErrorMatches(exception -> exception instanceof NoSuchElementException e
                                && e.getMessage().equals("customer.product.error.not_found"))
                        .verify();

        verify(productsClient).findProductById(1);
        verifyNoMoreInteractions(productsClient);
    }

}
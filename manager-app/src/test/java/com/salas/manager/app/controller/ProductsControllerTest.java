package com.salas.manager.app.controller;

import com.salas.manager.app.client.RestClientProductRestService;
import com.salas.manager.app.controller.payload.NewProductPayload;
import com.salas.manager.app.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {
    @Mock
    RestClientProductRestService productRestService;
    ProductsController productsController;


    @BeforeEach
    void init() {
        productsController = new ProductsController(productRestService);
    }

    @Test
    @DisplayName("create product new product and redirect to product page")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        var product = new NewProductPayload("product", "product");

        doReturn(new Product(1, "product", "product"))
                .when(productRestService)
                .createProduct(any(), any());

        String result = productsController.createProduct(product);
        assertEquals("redirect:/catalogue/products/list", result);
        verify(productRestService).createProduct(any(), any());
        verifyNoMoreInteractions(productRestService);
    }
}
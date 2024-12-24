package com.salas.manager.app.client;

import com.salas.manager.app.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestService {
    List<Product> findProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(int productId, String title, String details);

    void deleteProduct(int productId);
}

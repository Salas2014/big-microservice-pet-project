package com.salas.catalogue.service.services;


import com.salas.catalogue.service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String description);

    Optional<Product> findProductById(int productId);

    void updateProduct(Integer existProductId, String title, String details);

    void deleteById(Integer id);
}

package com.salas.catalogue.service.repo;

import com.salas.catalogue.service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProducts();

    Product save(Product product);

    Optional<Product> findProductById(int productId);

    void deleteById(Integer id);
}

package com.salas.catalogue.service.services;

import com.salas.catalogue.service.entity.Product;
import com.salas.catalogue.service.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class DefaultProductService implements ProductService {

    @Autowired
    private final ProductRepository repository;

    public DefaultProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAllProducts() {
        return repository.findAllProducts();
    }

    @Override
    public Product createProduct(String title, String description) {
        return repository.save(new Product(null, title, description));
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return repository.findProductById(productId);
    }

    @Override
    public void updateProduct(Integer existProductId, String title, String details) {
        repository.findProductById(existProductId)
                .ifPresentOrElse(product -> {
                    product.setTitle(title);
                    product.setDetails(details);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}

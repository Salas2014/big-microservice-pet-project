package com.salas.catalogue.service.services;

import com.salas.catalogue.service.entity.Product;
import com.salas.catalogue.service.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class DefaultProductService implements ProductService {

    @Autowired
    private final ProductRepository repository;

    public DefaultProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.repository.findAllByDetailsIsLikeIgnoreCase("%" + filter + "%");
        } else {
            return repository.findAll();
        }
    }

    @Override
    @Transactional
    public Product createProduct(String title, String description) {
        return repository.save(new Product(null, title, description));
    }

    @Override
    public Optional<Product> findProductById(int productId) {
        return repository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer existProductId, String title, String details) {
        repository.findById(existProductId)
                .ifPresentOrElse(product -> {
                    product.setTitle(title);
                    product.setDetails(details);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}

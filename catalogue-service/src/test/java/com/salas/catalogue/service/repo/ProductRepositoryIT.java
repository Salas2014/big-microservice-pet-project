package com.salas.catalogue.service.repo;

import com.salas.catalogue.service.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/products.sql")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryIT {

    @Autowired
    ProductRepository productRepository;


    @Test
    void findAllByDetailsIsLikeIgnoreCase() {
        String filter = "%Some%";

        Iterable<Product> products = productRepository.findAllByDetailsIsLikeIgnoreCase(filter);

        Product expected = new Product(2, "Some product 2", "Some2");

        assertEquals(List.of(expected), products);
    }

}
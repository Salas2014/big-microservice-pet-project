package com.salas.catalogue.service.repo;

import com.salas.catalogue.service.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Iterable<Product> findAllByDetailsIsLikeIgnoreCase(String filter);
}

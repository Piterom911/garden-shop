package com.predators.service;

import com.predators.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product create(Product product);

    Product getById(Long id);

    void delete(Long id);
}

package com.predators.service;

import com.predators.entity.Product;
import com.predators.repository.ProductJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductJpaRepository repository;

    public ProductServiceImpl(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {

        return   repository.findById(id).get();

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

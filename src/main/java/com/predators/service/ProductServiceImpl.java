package com.predators.service;

import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Category;
import com.predators.entity.Product;
import com.predators.exception.CategoryNotFoundException;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.ProductJpaRepository;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository repository;

    private final CategoryService categoryService;

    public ProductServiceImpl(ProductJpaRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product create(Product product) {
        Category category = categoryService.getById(product.getCategory().getId());
        product.setCategory(category);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with " + id + " Not Found"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Product update(Long id, ProductRequestDto productDto) {
        Category category = categoryService.getById(productDto.categoryId());
        if (category == null) {
            throw new CategoryNotFoundException("Category with " + id + " Not Found");
        }
        Product product = getById(id);
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setCategory(category);
        product.setUpdatedAt(Timestamp.from(Instant.now()));
        return repository.save(product);
    }
}

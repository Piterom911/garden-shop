package com.predators.service;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Category;
import com.predators.entity.Product;
import com.predators.exception.CategoryNotFoundException;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.ProductJpaRepository;
import com.predators.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository repository;

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Product> getAll(ProductFilterDto filter, int page, int size, String[] sortParams) {
        List<Sort.Order> orders = new ArrayList<>();

        for (String param : sortParams) {
            if (param == null || param.isBlank()) continue;

            String[] split = param.split(";");

            String field = split[0].trim();
            String direction = (split.length > 1) ? split[1].trim().toUpperCase() : "ASC";

            try {
                Sort.Direction dir = Sort.Direction.valueOf(direction);
                orders.add(new Sort.Order(dir, field));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sort direction: " + direction + " for field: " + field);
            }
        }

        if (orders.isEmpty()) {
            orders.add(new Sort.Order(Sort.Direction.ASC, "name")); // fallback
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Specification<Product> spec = ProductSpecification.withFilters(filter);
        return repository.findAll(spec, pageable);
    }

    @Override
    public Product create(Product product) {
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
    public Product update(Product product) {
        product.setUpdatedAt(Timestamp.from(Instant.now()));
        return repository.save(product);
    }

    @Override
    public void updateCategory(Long id, Category category) {
        Product product = getById(id);
        product.setCategory(category);
        repository.save(product);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return repository.findByCategory_Id(categoryId);
    }


}

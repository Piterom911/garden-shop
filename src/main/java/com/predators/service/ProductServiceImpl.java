package com.predators.service;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Category;
import com.predators.entity.Product;
import com.predators.exception.CategoryNotFoundException;
import com.predators.exception.ProductNotFoundException;
import com.predators.repository.ProductJpaRepository;
import com.predators.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository repository;

    private final CategoryService categoryService;

    public ProductServiceImpl(ProductJpaRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    @Override
    public Page<Product> getAll(ProductFilterDto filter, int page, int size, String[] sort) {
        Sort sorting = Sort.by(
                Arrays.stream(sort)
                        .map(s -> {
                            String[] split = s.split(",");
                            return new Sort.Order(Sort.Direction.fromString(split[1]), split[0]);
                        })
                        .collect(Collectors.toList())
        );
        Pageable pageable = PageRequest.of(page, size, sorting);

        Specification<Product> spec = ProductSpecification.withFilters(filter);

        return repository.findAll(spec,pageable);
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

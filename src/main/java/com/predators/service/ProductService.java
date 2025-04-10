package com.predators.service;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Page<Product> getAll(ProductFilterDto filter, int page, int size, String[] sort);

    Product create(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Long id, ProductRequestDto productDto);
}

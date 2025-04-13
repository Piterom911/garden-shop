package com.predators.service;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Product;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Page<Product> getAll(ProductFilterDto filter, int page, int size, String[] sort) throws BadRequestException;

    Product create(Product product);

    Product getById(Long id);

    void delete(Long id);

    Product update(Long id, ProductRequestDto productDto);
}

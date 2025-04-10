package com.predators.controller;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductResponseDto;
import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Product;
import com.predators.service.ProductService;
import com.predators.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    private final ProductService service;

    private final ProductConverter converter;

    public ProductController(ProductService service, ProductConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAll(
            @RequestParam ProductFilterDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {

        Page<Product> all = service.getAll(filter, page, size, sort);
        Page<ProductResponseDto> dtoPage = all.map(converter::toDto);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productDto) {
        Product product = converter.toEntity(productDto);
        Product createdProd = service.create(product);
        return new ResponseEntity<>(converter.toDto(createdProd), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        Product product = service.getById(id);
        return new ResponseEntity<>(converter.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody ProductRequestDto productDto) {
        Product update = service.update(id,productDto);
        return new ResponseEntity<>(converter.toDto(update), HttpStatus.CREATED);
    }
}

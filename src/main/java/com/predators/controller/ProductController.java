package com.predators.controller;

import com.predators.dto.product.ProductResponseDto;
import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductRequestDto;
import com.predators.entity.Product;
import com.predators.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        List<ProductResponseDto> dtoList = service.getAll().stream()
                .map(converter::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productDto) {
        Product product = converter.toEntity(productDto);
        Product createdProd = service.create(product);
        return ResponseEntity.ok(converter.toDto(createdProd));
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
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, ProductRequestDto productDto) {
        Product update = service.update(id,productDto);
        return new ResponseEntity<>(converter.toDto(update), HttpStatus.CREATED);
    }
}

package com.predators.controller;

import com.predators.dto.category.CategoryRequestDto;
import com.predators.dto.category.CategoryResponceDto;
import com.predators.dto.converter.CategoryConverter;
import com.predators.entity.Category;
import com.predators.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/categories")
public class CategoryController {

    private final CategoryService service;

    private final CategoryConverter converter;

    public CategoryController(CategoryService categoryService, CategoryConverter converter) {
        this.service = categoryService;
        this.converter = converter;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryResponceDto>> getAll() {
        List<CategoryResponceDto> dtolist = service.getAll().stream()
                .map(converter::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtolist, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponceDto> getById(@PathVariable Long id) {
        Category category = service.getById(id);
        return new ResponseEntity<>(converter.toDto(category), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponceDto> create(@RequestBody CategoryRequestDto categoryDto) {
        Category category = converter.toEntity(categoryDto);
        Category createdCategory = service.create(category);
        return ResponseEntity.ok(converter.toDto(createdCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
package com.predators.controller;

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
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.create(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.predators.controller;

import com.predators.dto.category.CategoryMapper;
import com.predators.dto.category.CategoryRequestDto;
import com.predators.dto.category.CategoryResponseDto;
import com.predators.entity.Category;
import com.predators.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryApi{

    private final CategoryService service;

    @Qualifier("categoryMapper")
    private final CategoryMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<CategoryResponseDto> dtolist = service.getAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtolist, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        Category category = service.getById(id);
        return new ResponseEntity<>(mapper.toDto(category), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryRequestDto categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        Category createdCategory = service.create(category);
        return new ResponseEntity<>(mapper.toDto(createdCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<CategoryResponseDto> update(@PathVariable(name = "id") Long id, @RequestParam String name) {
        Category category = service.update(id, name);
        return new ResponseEntity<>(mapper.toDto(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
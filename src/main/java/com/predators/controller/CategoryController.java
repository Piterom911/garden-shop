package com.predators.controller;

import com.predators.dto.category.CategoryMapper;
import com.predators.dto.category.CategoryRequestDto;
import com.predators.dto.category.CategoryResponseDto;
import com.predators.entity.Category;
import com.predators.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/categories")
@Tag(name = "Category Management", description = "Operations for managing product categories")
public class CategoryController {

    private final CategoryService service;

    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.service = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all categories", description = "Retrieves a list of all available product categories")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class))))
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<CategoryResponseDto> dtolist = service.getAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(dtolist, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its unique identifier")
    @Parameter(name = "id", description = "ID of the category to retrieve", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the category",
            content = @Content(schema = @Schema(implementation = CategoryResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        Category category = service.getById(id);
        return new ResponseEntity<>(mapper.toDto(category), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create new category (Admin only)", description = "Creates a new product category. Requires ADMIN role.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category details", required = true,
            content = @Content(schema = @Schema(implementation = CategoryRequestDto.class)))
    @ApiResponse(responseCode = "201", description = "Successfully created the category",
            content = @Content(schema = @Schema(implementation = CategoryResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN privileges"
    )
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryRequestDto categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        Category createdCategory = service.create(category);
        return new ResponseEntity<>(mapper.toDto(createdCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category name (Admin only)",
            description = "Updates the name of an existing category. Requires ADMIN role.")
    @Parameter(name = "id", description = "ID of the category to update", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @Parameter(name = "name", description = "New category name", required = true,
            schema = @Schema(type = "string", example = "Pots and planters"))
    @ApiResponse(responseCode = "200", description = "Successfully updated the category",
            content = @Content(schema = @Schema(implementation = CategoryResponseDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN privileges")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable(name = "id") Long id, @RequestParam String name) {
        Category category = service.update(id, name);
        return new ResponseEntity<>(mapper.toDto(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete category (Admin only)", description = "Deletes a category by its ID. Requires ADMIN role.")
    @Parameter(name = "id", description = "ID of the category to delete", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully deleted the category")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN privileges")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
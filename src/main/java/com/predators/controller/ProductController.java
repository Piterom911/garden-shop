package com.predators.controller;

import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductRequestDto;
import com.predators.dto.product.ProductResponseDto;
import com.predators.entity.Product;
import com.predators.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Product Management", description = "Operations for managing products")
public class ProductController {

    private final ProductService service;

    private final ProductConverter converter;

    public ProductController(ProductService service, ProductConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping()
    @Operation(summary = "Get paginated products",
            description = "Retrieves a paginated list of products with filtering and sorting options")
    @Parameters({
            @Parameter(name = "page", description = "Page number (0-based)", example = "0", schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "Number of items per page", example = "10", schema = @Schema(type = "integer", defaultValue = "10")),
            @Parameter(name = "sort", description = "Sorting criteria in format: property,direction. Multiple sort params allowed",
                    example = "name,asc", schema = @Schema(type = "string", defaultValue = "name,asc"))})
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid filter or sort parameters")
    public ResponseEntity<Page<ProductResponseDto>> getAll(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(defaultValue = "name;asc", name = "sort") String[] sort,
            @ModelAttribute ProductFilterDto filter
    ) throws BadRequestException {
        Page<Product> all = service.getAll(filter, page, size, sort);
        Page<ProductResponseDto> dtoPage = all.map(converter::toDto);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create new product (Admin only)",
            description = "Creates a new product. Requires ADMIN role.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product details", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
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
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its unique identifier")
    @Parameter(name = "id", description = "ID of the product to retrieve", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the product",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product with new data")
    @Parameter(name = "id", description = "ID of the product to update", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated product details", required = true,
            content = @Content(schema = @Schema(implementation = ProductRequestDto.class)))
    @ApiResponse(responseCode = "201", description = "Product updated successfully",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(
            responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductResponseDto> update(@PathVariable(name = "id") Long id,
                                                     @RequestBody ProductRequestDto productDto) {

        Product update = service.update(id, productDto);

        return new ResponseEntity<>(converter.toDto(update), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Set product discount (Admin only)", description = "Sets discount price for a product. Requires ADMIN role.")
    @Parameter(name = "id", description = "ID of the product to update", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @Parameter(name = "discount", description = "Discount amount to set", required = true,
            schema = @Schema(type = "number", format = "decimal", example = "15.99"))
    @ApiResponse(responseCode = "202", description = "Discount set successfully",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
//    @ApiResponse(responseCode = "400", description = "Invalid discount value")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductResponseDto> setDiscount(@PathVariable Long id, @RequestParam BigDecimal discount) {
        Product product = service.setDiscount(id, discount);
        return new ResponseEntity<>(converter.toDto(product), HttpStatus.ACCEPTED);
    }

    @GetMapping("/day-product")
    @Operation(summary = "Get product of the day", description = "Retrieves the current product of the day")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved product of the day",
            content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Product of the day not set")
    public ResponseEntity<ProductResponseDto> getDayProduct() {
        Product dayProduct = service.getDayProduct();
        return new ResponseEntity<>(converter.toDto(dayProduct), HttpStatus.OK);
    }
}

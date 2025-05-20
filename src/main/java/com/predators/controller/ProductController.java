package com.predators.controller;

import com.predators.dto.product.ProductFilterDto;
import com.predators.dto.product.ProductMapper;
import com.predators.dto.product.ProductRequestDto;
import com.predators.dto.product.ProductResponseDto;
import com.predators.entity.Product;
import com.predators.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi{

    private final ProductService service;

    @Qualifier("productMapperImpl")
    private final ProductMapper mapper;

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDto>> getAll(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(defaultValue = "name;asc", name = "sort") String[] sort,
            @ModelAttribute ProductFilterDto filter
    ) throws BadRequestException {
        Page<Product> all = service.getAll(filter, page, size, sort);
        Page<ProductResponseDto> dtoPage = all.map(mapper::toDto);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productDto) {
        Product product = mapper.toEntity(productDto);
        Product createdProd = service.create(product);
        return new ResponseEntity<>(mapper.toDto(createdProd), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        Product product = service.getById(id);
        return new ResponseEntity<>(mapper.toDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable(name = "id") Long id,
                                                     @RequestBody ProductRequestDto productDto) {
        Product update = service.update(id, productDto);
        return new ResponseEntity<>(mapper.toDto(update), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDto> setDiscount(@PathVariable Long id, @RequestParam BigDecimal discount) {
        Product product = service.setDiscount(id, discount);
        return new ResponseEntity<>(mapper.toDto(product), HttpStatus.ACCEPTED);
    }

    @GetMapping("/day-product")
    public ResponseEntity<ProductResponseDto> getDayProduct() {
        Product dayProduct = service.getDayProduct();
        return new ResponseEntity<>(mapper.toDto(dayProduct), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDto>> filterProducts(@ModelAttribute ProductFilterDto filter) {
        List<ProductResponseDto> result = service.getAllFiltered(filter);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

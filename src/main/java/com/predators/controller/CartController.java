package com.predators.controller;

import com.predators.dto.cart.ProductToCartRequestDto;
import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductResponseDto;
import com.predators.entity.CartItem;
import com.predators.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    private final ProductConverter productConverter;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productsResponse =
                service.getAllProducts().stream().map(productConverter::toDto).toList();
        return ResponseEntity.ok(productsResponse);
    }

    @PostMapping
    public ResponseEntity<CartItem> addProduct(@RequestBody ProductToCartRequestDto productToCartRequestDto) {
        return ResponseEntity.ok(service.addProduct(productToCartRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}

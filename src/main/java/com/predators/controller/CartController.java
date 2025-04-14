package com.predators.controller;

import com.predators.dto.cart.ProductToCartRequestDto;
import com.predators.entity.CartItem;
import com.predators.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
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

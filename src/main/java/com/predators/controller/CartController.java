package com.predators.controller;

import com.predators.dto.cart.CartRequestDto;
import com.predators.dto.cart.CartResponseDto;
import com.predators.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartResponseDto> add(@RequestBody CartRequestDto cartRequestDto) {

        return null;
    }
}

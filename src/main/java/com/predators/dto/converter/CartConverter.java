package com.predators.dto.converter;

import com.predators.dto.cart.CartRequestDto;
import com.predators.dto.cart.CartResponseDto;
import com.predators.entity.Cart;

public class CartConverter implements Converter<CartRequestDto, CartResponseDto, Cart> {

    @Override
    public CartResponseDto toDto(Cart cart) {
        return null;
    }

    @Override
    public Cart toEntity(CartRequestDto cartRequestDto) {

        return null;
    }
}

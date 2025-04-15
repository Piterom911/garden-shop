package com.predators.dto.converter;

import com.predators.dto.cart.ProductToCartRequestDto;
import com.predators.dto.cart.CartResponseDto;
import com.predators.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter implements Converter<ProductToCartRequestDto, CartResponseDto, Cart> {

    @Override
    public CartResponseDto toDto(Cart cart) {
        return null;
    }

    @Override
    public Cart toEntity(ProductToCartRequestDto productToCartRequestDto) {

        return null;
    }
}

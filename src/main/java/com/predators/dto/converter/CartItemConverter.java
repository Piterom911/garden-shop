package com.predators.dto.converter;

import com.predators.dto.CartItemRequestDto;
import com.predators.dto.CartItemResponseDto;
import com.predators.entity.CartItem;
import com.predators.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemConverter {
    private final ProductService productService;

//    public CartItem toEntity(CartItemRequestDto cartItemDto, Long cartId) {
//        productService.getById(cartItemDto.productId());
//
//        return CartItem.builder()
//                .id(cartI.)
//                .productId(cartItemDto.productId())
//                .quantity(cartItemDto.quantity())
//                .build();
//    }
//
//    public CartItemResponseDto toDto(CartItem cartItem) {
//        return new CartItemResponseDto(
//                cartItem.getId(),
//                cartItem.getCartId(),
//                cartItem.getProductId(),
//                cartItem.getQuantity()
//        );
//    }
}

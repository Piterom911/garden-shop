package com.predators.dto.cartItem;

import com.predators.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "quantity", target = "quantity")
    CartItemResponseDto toDto(CartItem cartItem);
}

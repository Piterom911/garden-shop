package com.predators.dto.orderitem;

import com.predators.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "priceAtPurchase", target = "priceAtPurchase")
    OrderItemResponseDto toDto(OrderItem orderItem);
}

package com.predators.dto.orderitem;

import com.predators.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toDto(OrderItem orderItem);
}

package com.predators.dto.converter;

import com.predators.dto.order.OrderItemDto;
import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.order.OrderResponseDto;
import com.predators.entity.Order;
import com.predators.entity.OrderItem;
import com.predators.entity.ShopUser;
import com.predators.entity.enums.OrderStatus;
import com.predators.service.ShopUserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConverter implements Converter<OrderRequestDto, OrderResponseDto, Order> {

    private final ShopUserService shopUserService;

    public OrderConverter(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    public Order toEntity(OrderRequestDto dto) {
        ShopUser user = shopUserService.getById(dto.userId());
        return Order.builder()
                .user(user)
                .status(OrderStatus.valueOf(dto.status()))
                .totalAmount(dto.totalAmount())
                .build();
    }

    public OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(toItemDtoList(order.getOrderItems()))
                .build();
    }

    private List<OrderItemDto> toItemDtoList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::toOrderItemDto)
                .toList();
    }

    private OrderItemDto toOrderItemDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPriceAtPurchase()
        );
    }
}

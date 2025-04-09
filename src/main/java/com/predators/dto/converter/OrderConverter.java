package com.predators.dto.converter;

import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.order.OrderResponseDto;
import com.predators.entity.Order;
import com.predators.entity.User;
import com.predators.entity.enums.OrderStatus;
import com.predators.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements Converter<OrderRequestDto, OrderResponseDto, Order> {

    private final UserService userService;

    public OrderConverter(UserService userService) {
        this.userService = userService;
    }

    public Order toEntity(OrderRequestDto dto) {
        User user = userService.getById(dto.userId());
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
                .build();
    }
}

package com.predators.dto.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequestDto(
        Long userId,
        String status,
        BigDecimal totalAmount
) {}

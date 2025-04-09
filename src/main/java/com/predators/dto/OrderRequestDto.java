package com.predators.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequestDto(
        Long userId,
        String status,
        BigDecimal totalAmount
) {}

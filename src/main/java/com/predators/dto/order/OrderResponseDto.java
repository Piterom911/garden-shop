package com.predators.dto.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
public record OrderResponseDto(
        Long id,
        Long userId,
        String status,
        BigDecimal totalAmount,
        Timestamp createdAt,
        Timestamp updatedAt
) {}

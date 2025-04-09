package com.predators.dto;

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

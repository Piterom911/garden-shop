package com.predators.dto;

import lombok.*;
import java.math.BigDecimal;

@Builder
public record ProductRequestDto(String name,
                                String description,
                                BigDecimal price,
                                Long categoryId,
                                String imageUrl
                                ) {
}

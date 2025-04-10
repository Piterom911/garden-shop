package com.predators.dto.category;

import com.predators.entity.Product;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(Long id,
                                  String name,
                                  List<Product> products) {
}

package com.predators.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.predators.entity.Product;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryResponseDto(Long id,
                                  String name,
                                  List<Product> products) {
}

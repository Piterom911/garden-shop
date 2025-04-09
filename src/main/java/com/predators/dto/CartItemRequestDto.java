package com.predators.dto;

import groovy.transform.builder.Builder;

@Builder
public record CartItemRequestDto(Long productId,
                                 Integer quantity) {
}

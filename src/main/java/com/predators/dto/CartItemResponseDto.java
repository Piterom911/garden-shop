package com.predators.dto;

import groovy.transform.builder.Builder;


public record CartItemResponseDto(Long id,
                                  Long cartId,
                                  Long productId,
                                  Integer quantity) {
}

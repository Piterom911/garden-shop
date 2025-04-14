package com.predators.dto.cart;

public record ProductToCartRequestDto(Long productId,
                                      Integer quantity) {
}

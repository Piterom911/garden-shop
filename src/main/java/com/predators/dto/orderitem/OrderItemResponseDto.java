package com.predators.dto.orderitem;

import java.math.BigDecimal;

public record OrderItemResponseDto(Long id,
                                   Long productId,
                                   int quantity,
                                   BigDecimal priceAtPurchase) {
}

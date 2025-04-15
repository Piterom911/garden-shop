package com.predators.dto.order;

import java.math.BigDecimal;

public record OrderItemDto(Long id,

                           Long productId,

                           String productName,

                           Integer quantity,

                           BigDecimal priceAtPurchase) {
}

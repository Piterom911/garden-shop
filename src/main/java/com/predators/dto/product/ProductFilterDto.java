package com.predators.dto.product;

import com.predators.entity.Category;
import java.math.BigDecimal;

public record ProductFilterDto (BigDecimal minPrice,
                                BigDecimal maxPrice,
                                boolean discount,
                                Category category) {

}

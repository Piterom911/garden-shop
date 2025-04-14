package com.predators.dto.product;

import com.predators.entity.Category;
import java.math.BigDecimal;

public record ProductFilterDto (BigDecimal minPrice,
                                BigDecimal maxPrice,
                                Boolean discount,
                                Category category) {

}

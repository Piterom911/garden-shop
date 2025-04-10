package com.predators.specification;

import com.predators.dto.product.ProductFilterDto;
import com.predators.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilters(ProductFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice()));
            }

            if (filter.maxPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.maxPrice()));
            }

            if (filter.category() != null) {
                predicates.add(cb.equal(root.get("category"), filter.category()));
            }

            if (filter.discount()) {
                predicates.add(cb.equal(root.get("discount"), true));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.predators.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.security.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    private String imageUrl;

    private BigDecimal discountPrice;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}

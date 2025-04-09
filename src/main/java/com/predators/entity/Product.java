package com.predators.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String imageUrl;

    private BigDecimal discountPrice;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<OrderItem> orderItem;
}

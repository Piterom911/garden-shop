package com.predators.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    private String imageUrl;

    private BigDecimal discountPrice;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @JsonBackReference
    private List<OrderItem> orderItem;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "product")
    @JsonBackReference
    private Set<Favorite> favorites = new HashSet<>();

    @Override
    public int compareTo(Product other) {
        return other.discountPrice.intValue() - this.discountPrice.intValue();
    }
}

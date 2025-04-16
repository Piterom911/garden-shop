package com.predators.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.predators.entity.enums.DeliveryMethod;
import com.predators.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ShopUser user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Timestamp createdAt;

    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    private String contactPhone;

    private Timestamp updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @JsonBackReference
    private List<OrderItem> orderItems;
}

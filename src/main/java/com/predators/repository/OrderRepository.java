package com.predators.repository;

import com.predators.dto.product.ProductCountDto;
import com.predators.entity.Order;
import com.predators.entity.Product;
import com.predators.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.status = :status")
    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByUser_Id(Long userId);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.updatedAt > :afterDate")
    List<Order> findAllByStatusAndAfterDate(OrderStatus status, Timestamp afterDate);

    @Query("SELECT new com.predators.dto.product.ProductCountDto(i.product, COUNT(i.id)) FROM Order o " +
            "JOIN o.orderItems i " +
            "WHERE o.status = :status " +
            "GROUP BY i.product " +
            "ORDER BY COUNT(i.id) DESC " +
            "LIMIT :limit")
    List<ProductCountDto> findTopProductsAndCountsByOrderStatus(OrderStatus status, int limit);

    @Query("SELECT DISTINCT i.product FROM Order o " +
            "JOIN o.orderItems i " +
            "WHERE o.status = :status AND o.updatedAt < :date")
    Set<Product> findProductsFromOrdersByStatusAndUpdatedAtBefore(OrderStatus status, Timestamp date);
}

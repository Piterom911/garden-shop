package com.predators.repository;

import com.predators.entity.Order;
import com.predators.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.status = :status")
    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByUser_Id(Long userId);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.updatedAt > :afterDate")
    List<Order> findAllByStatusAndAfterDate(OrderStatus status, Timestamp afterDate);


}

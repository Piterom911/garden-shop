package com.predators.service;

import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.product.ProductCountDto;
import com.predators.entity.Order;
import com.predators.entity.Product;
import com.predators.entity.enums.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface OrderService {

    List<Order> getAll();

    Order getById(Long id);

    Order create(OrderRequestDto dto);

    void delete(Long id);

    Order updateStatus(Long id, OrderStatus status);

    String getStatus(Long id);

    List<Order> getAllByStatus(OrderStatus status);

    List<Order> getHistory();

    List<Order> getAllByStatusAndAfterDate(OrderStatus status, Timestamp afterDate);

    Order cancel(Long id);

    List<ProductCountDto> findTopProductsAndCountsByOrderStatus(OrderStatus status, int limit);

    Set<Product> findByStatusAndUpdatedAtBeforeThreshold(OrderStatus status, Timestamp data);
}

package com.predators.service;

import com.predators.dto.order.OrderRequestDto;
import com.predators.entity.Order;
import java.util.List;

public interface OrderService {

    List<Order> getAll();

    Order getById(Long id);

    Order create(OrderRequestDto dto);

    void delete(Long id);

    Order updateStatus(Long id, String newStatus);

}

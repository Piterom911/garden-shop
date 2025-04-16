package com.predators.service;

import com.predators.dto.cart.ProductToItemDto;
import com.predators.dto.converter.OrderConverter;
import com.predators.dto.order.OrderRequestDto;
import com.predators.entity.Order;
import com.predators.entity.OrderItem;
import com.predators.entity.Product;
import com.predators.entity.enums.OrderStatus;
import com.predators.exception.OrderNotFoundException;
import com.predators.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemService orderItemService;

    private final OrderConverter orderConverter;

    private final ProductService productService;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional
    public Order create(OrderRequestDto dto) {
        Order entity = orderConverter.toEntity(dto);
        Order savedOrder = orderRepository.save(entity);

        List<OrderItem> items = new ArrayList<>();
        for (ProductToItemDto productDto : dto.items()) {
            Product product = productService.getById(productDto.productId());
            BigDecimal discount = product.getDiscountPrice() == null ? BigDecimal.ZERO : product.getDiscountPrice();
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(productDto.quantity())
                    .priceAtPurchase(product.getPrice()
                            .subtract(discount)
                            .multiply(BigDecimal.valueOf(productDto.quantity())))
                    .build();

            orderItemService.create(orderItem);
            items.add(orderItem);
        }
        savedOrder.setOrderItems(items);
        return savedOrder;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateStatus(Long id, String newStatus) {
        Order order = getById(id);
        order.setStatus(OrderStatus.valueOf(newStatus));
        order.setUpdatedAt(Timestamp.from(Instant.now()));
        return orderRepository.save(order);
    }
}

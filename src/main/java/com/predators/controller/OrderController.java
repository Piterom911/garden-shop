package com.predators.controller;

import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.order.OrderResponseDto;
import com.predators.dto.converter.OrderConverter;
import com.predators.entity.Order;
import com.predators.entity.enums.OrderStatus;
import com.predators.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter converter;

    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.converter = orderConverter;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        List<OrderResponseDto> list = orderService.getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get-status/{status}")
    public ResponseEntity<List<Order>> getStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getAllByStatus(OrderStatus.valueOf(status.toUpperCase())));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto dto) {
        Order created = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(converter.toDto(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateStatus(@PathVariable Long id,
                                                         @RequestParam String status) {
        Order updated = orderService.updateStatus(id, OrderStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(converter.toDto(updated));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getStatus(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

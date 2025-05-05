package com.predators.controller;

import com.predators.dto.converter.OrderConverter;
import com.predators.dto.order.OrderRequestDto;
import com.predators.dto.order.OrderResponseDto;
import com.predators.entity.Order;
import com.predators.entity.enums.OrderStatus;
import com.predators.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
@Tag(name = "Order Management", description = "Operations for managing customer orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter converter;

    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.converter = orderConverter;
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class))))
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        List<OrderResponseDto> list = orderService.getAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/history")
    @Operation(summary = "Get order history", description = "Retrieves order history for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved order history",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class))))
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory() {
        List<OrderResponseDto> list = orderService.getHistory()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }


    @GetMapping("/get-status/{status}")
    @Operation(summary = "Get orders by status", description = "Retrieves orders filtered by their status")
    @Parameter(name = "status", description = "Order status to filter by (e.g., CREATED, PENDING, COMPLETED)",
            required = true, schema = @Schema(type = "string", example = "PENDING"))
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    public ResponseEntity<List<Order>> getStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getAllByStatus(OrderStatus.valueOf(status.toUpperCase())));
    }

    @PostMapping
    @Operation(summary = "Create new order", description = "Creates a new order. Requires CLIENT role.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order details", required = true,
            content = @Content(schema = @Schema(implementation = OrderRequestDto.class)))
    @ApiResponse(responseCode = "201", description = "Order created successfully",
            content = @Content(schema = @Schema(implementation = OrderResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid order data")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires CLIENT role")
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto dto) {
        Order created = orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter.toDto(created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieves a specific order by its unique identifier")
    @Parameter(name = "id", description = "ID of the order to retrieve", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the order",
            content = @Content(schema = @Schema(implementation = OrderResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(converter.toDto(order));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order status (Admin only)", description = "Updates the status of an existing order. Requires ADMIN role.")
    @Parameter(name = "id", description = "ID of the order to update", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @Parameter(name = "status", description = "New order status", required = true,
            schema = @Schema(type = "string", example = "COMPLETED"))
    @ApiResponse(responseCode = "200", description = "Successfully updated order status",
            content = @Content(schema = @Schema(implementation = OrderResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderResponseDto> updateStatus(@PathVariable Long id,
                                                         @RequestParam String status) {
        Order updated = orderService.updateStatus(id, OrderStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(converter.toDto(updated));
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Get order status", description = "Retrieves the current status of a specific order")
    @Parameter(name = "id", description = "ID of the order", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully retrieved order status",
            content = @Content(schema = @Schema(type = "string", example = "COMPLETED")))
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getStatus(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete order (Admin only)", description = "Deletes an order by its ID. Requires ADMIN role.")
    @Parameter(name = "id", description = "ID of the order to delete", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
    @ApiResponse(responseCode = "200", description = "Successfully deleted the order")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package com.predators.controller;

import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductResponseDto;
import com.predators.entity.enums.OrderStatus;
import com.predators.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/report")
@RequiredArgsConstructor
@Tag(name = "Report Generation", description = "Operations for generating various reports")
public class ReportController {

    private final ReportService reportService;

    private final ProductConverter converter;

    @GetMapping("/top-product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get top products by order status (Admin only)",
            description = "Retrieves a list of top products based on the provided order status. Requires ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of top products",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class))))
    @ApiResponse(responseCode = "400", description = "Invalid order status provided",
            content = @Content(schema = @Schema(type = "string", example = "Invalid order status: UNKNOWN_STATUS")))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role",
            content = @Content(schema = @Schema(type = "string", example = "Access Denied")))
    public ResponseEntity<List<ProductResponseDto>> getTopProduct(@RequestParam String status) {
        List<ProductResponseDto> list = reportService.topItems(OrderStatus.valueOf(status.toUpperCase())).stream()
                .map(converter::toDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/waiting-payment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get products waiting for payment for more than N days (Admin only)",
            description = "Retrieves a list of products that have been waiting for payment for more than the specified number of days. Requires ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products waiting for payment",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class))))
    @ApiResponse(responseCode = "400", description = "Invalid number of days provided",
            content = @Content(schema = @Schema(type = "string", example = "Days must be a positive integer")))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role",
            content = @Content(schema = @Schema(type = "string", example = "Access Denied")))
    public ResponseEntity<List<ProductResponseDto>> waitingPaymentMoreNDays(@RequestParam Long days) {
        List<ProductResponseDto> products = reportService.waitingPaymentMoreNDays(days).stream()
                .map(converter::toDto).toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/profit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get profit (Admin only)",
            description = "Retrieves the total profit, optionally filtered by day, month, or year. Requires ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the profit",
            content = @Content(schema = @Schema(type = "number", format = "float", example = "15000.75")))
    @ApiResponse(responseCode = "400", description = "Invalid date parameters provided",
            content = @Content(schema = @Schema(type = "string", example = "Invalid day or month value")))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role",
            content = @Content(schema = @Schema(type = "string", example = "Access Denied")))
    public ResponseEntity<BigDecimal> getProfit(@RequestParam(name = "day", required = false) Long day,
                                                @RequestParam(name = "month", required = false) Long month,
                                                @RequestParam(name = "year", required = false) Long year) {
        BigDecimal profit = reportService.getProfit(day, month, year);
        return new ResponseEntity<>(profit, HttpStatus.OK);
    }
}

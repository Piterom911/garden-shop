package com.predators.controller;

import com.predators.dto.cart.ProductToItemDto;
import com.predators.dto.converter.ProductConverter;
import com.predators.dto.product.ProductResponseDto;
import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Cart Management", description = "Operations related to shopping cart functionality")
public class CartController {

    private final CartService service;

    private final ProductConverter productConverter;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all carts (Admin only)",
            description = "Retrieves all shopping carts in the system. Requires ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all carts",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cart.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN privileges"
    )
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> allCarts = service.getAll();
        return new ResponseEntity<>(allCarts, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get current user's cart products",
            description = "Retrieves all products in the authenticated user's shopping cart")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cart products",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class))))
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productsResponse =
                service.getAllProducts().stream().map(productConverter::toDto).toList();
        return ResponseEntity.ok(productsResponse);
    }

    @PostMapping
    @Operation(summary = "Add product to cart",
            description = "Adds a product with specified quantity to the user's shopping cart")
    @ApiResponse(responseCode = "200", description = "Successfully added product to cart",
            content = @Content(schema = @Schema(implementation = CartItem.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<CartItem> addProduct(@RequestBody ProductToItemDto productToItemDto) {
        return ResponseEntity.ok(service.addProduct(productToItemDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove product from cart",
            description = "Removes a specific product from the user's shopping cart")
    @Parameter(name = "id", description = "ID of the product to remove", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "123"))
    @ApiResponse(responseCode = "200", description = "Successfully removed product from cart")
    @ApiResponse(responseCode = "404", description = "Product not found in cart")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/current-cart/{id}")
    @Operation(summary = "Delete cart by ID",
            description = "Completely removes a shopping cart with specified ID")
    @Parameter(name = "id", description = "ID of the cart to remove", required = true,
            schema = @Schema(type = "integer", format = "int64", example = "456"))
    @ApiResponse(responseCode = "204", description = "Successfully deleted cart")
    @ApiResponse(responseCode = "404", description = "Cart not found")
    public ResponseEntity<Void> deleteCartById(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

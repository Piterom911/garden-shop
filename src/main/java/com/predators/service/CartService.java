package com.predators.service;

import com.predators.dto.cart.ProductToCartRequestDto;
import com.predators.entity.Cart;
import com.predators.entity.CartItem;

import java.util.List;

public interface CartService {

    List<Cart> getAll();

    CartItem addProduct(ProductToCartRequestDto productToCartRequestDto);

    void deleteProduct(Long productId);
}

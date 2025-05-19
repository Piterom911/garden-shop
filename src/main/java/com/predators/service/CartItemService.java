package com.predators.service;

import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface CartItemService {

    List<CartItem> getAll();

    Set<CartItem> getAllByCart(Cart cart);

    CartItem getById(Long id);

    CartItem create(CartItem cartItem);

    void delete(Long id);

    Optional<CartItem> findByProduct_Id(Long productId);

    CartItem findCartItemByProductAndCart(Product product, Cart cart);

    CartItem getByProductIdAndCartId(Long productId, Long cartId);
}


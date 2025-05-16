package com.predators.repository;

import com.predators.entity.Cart;
import com.predators.entity.CartItem;
import com.predators.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProduct_Id(Long productId);

    Optional<Set<CartItem>> findAllByCart(Cart cart);

    Optional<CartItem> findCartItemByProductAndCart(Product product, Cart cart);

    CartItem findCartItemByProduct_IdAndCart_Id(Long productId, Long cartId);
}

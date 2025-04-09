package com.predators.service;

import com.predators.entity.CartItem;
import com.predators.entity.Category;
import com.predators.repository.CartItemJpaRepository;
import com.predators.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemJpaRepository cartItemJpaRepository;

    @Override
    public List<CartItem> getAll() {
        return cartItemJpaRepository.findAll();
    }

    @Override
    public CartItem getById(Long id) {
        return cartItemJpaRepository.findById(id).
                orElseThrow(() -> new RuntimeException("CartItem Not Found"));
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return cartItemJpaRepository.save(new CartItem());
    }

    @Override
    public void delete(Long id) {
        if (!cartItemJpaRepository.existsById(id)) {
            throw new RuntimeException("CartItem Not Found");
        }
        cartItemJpaRepository.deleteById(id);
    }
}

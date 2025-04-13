package com.predators.service;

import com.predators.entity.Cart;
import com.predators.repository.CartJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartJpaRepository repository;

    public CartServiceImpl(CartJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cart> getAll() {
        return repository.findAll();
    }
}

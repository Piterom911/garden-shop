package com.predators.service;

import com.predators.entity.CartItem;
import com.predators.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {

    List<CartItem> getAll();

    CartItem getById(Long id);

    CartItem create(CartItem cartItem);

    void delete(Long id);
}


package com.predators.service;


import com.predators.entity.Favorite;
import com.predators.entity.Product;
import com.predators.entity.ShopUser;
import com.predators.exception.FavoriteNotFoundException;
import com.predators.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final ProductService productService;

    private final ShopUserService shopUserService;

    @Override
    public List<Favorite> getAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorite create(Long productId) {
        ShopUser currentUser = shopUserService.getCurrentUser();
        Product byId = productService.getById(productId);
        Favorite favorite = Favorite.builder()
                .user(currentUser)
                .product(byId)
                .build();
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite getById(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteNotFoundException("Favorite not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        favoriteRepository.deleteById(id);
    }


}

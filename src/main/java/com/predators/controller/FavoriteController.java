package com.predators.controller;

import com.predators.entity.Favorite;
import com.predators.service.FavoriteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public List<Favorite> getAll() {
        return favoriteService.getAll();
    }

    @PostMapping
    public Favorite create(@RequestBody Favorite favorite) {
        return favoriteService.create(favorite);
    }

    @GetMapping("/{id}")
    public Favorite getById(@PathVariable Long id) {
        return favoriteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        favoriteService.delete(id);
    }
}

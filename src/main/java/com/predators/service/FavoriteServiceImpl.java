package com.predators.service;


import com.predators.entity.Favorite;
import com.predators.exception.FavoriteNotFoundException;
import com.predators.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public List<Favorite> getAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorite create(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite getById(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(()-> new FavoriteNotFoundException("Favorite not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        favoriteRepository.deleteById(id);
    }
}

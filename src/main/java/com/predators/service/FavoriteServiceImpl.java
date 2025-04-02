package com.predators.service;


import com.predators.entity.Favorite;
import com.predators.exception.ResourceNotFoundException;
import com.predators.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service                                                      //Spring будет управлять этим бином
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;         //Через конструктор внедряем репозиторий
    }

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();                  //Метод для получения всех избранных
    }

    @Override
    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);             //Метод для создания новой записи
    }

    @Override
    public Favorite getFavoriteById(Long id) {                //Получить Favorite по ID
        Optional<Favorite> favorite = favoriteRepository.findById(id);
        return favoriteRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Favorite not found with id: " + id));
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);                    //Удалить Favorite по ID
    }
}

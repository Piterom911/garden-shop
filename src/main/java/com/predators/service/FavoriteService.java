package com.predators.service;

import com.predators.entity.Favorite;
import java.util.List;

public interface FavoriteService {

    List<Favorite>getAllFavorites();                               // получить все избранные

    Favorite createFavorite(Favorite favorite);                    // создать новое избранное

    Favorite getFavoriteById(Long id);                             // получить избранное по ID

    void deleteFavorite(Long id);                                  // удалить избранное по ID
}

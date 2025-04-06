package com.predators.service;

import com.predators.entity.Favorite;
import com.predators.exception.FavoriteNotFoundException;
import com.predators.repository.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Test
    void testGetAllFavorites() {
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(new Favorite(), new Favorite()));

        List<Favorite> result = favoriteService.getAll();

        assertEquals(2, result.size());
        verify(favoriteRepository, times(1)).findAll();
    }

    @Test
    void testCreateFavorite() {
        Favorite favorite = new Favorite();
        when(favoriteRepository.save(favorite)).thenReturn(favorite);

        Favorite result = favoriteService.create(favorite);

        assertEquals(favorite, result);
        verify(favoriteRepository, times(1)).save(favorite);
    }

    @Test
    void testGetFavoriteById_WhenExists() {
        Favorite favorite = new Favorite();
        when(favoriteRepository.findById(1L)).thenReturn(Optional.of(favorite));

        Favorite result = favoriteService.getById(1L);

        assertEquals(favorite, result);
        verify(favoriteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFavoriteById_WhenNotFound() {
        when(favoriteRepository.findById(99L)).thenReturn(Optional.empty());

        FavoriteNotFoundException exception = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteService.getById(99L));

        assertEquals("Favorite not found with id: 99", exception.getMessage());
    }

    @Test
    void testDeleteFavorite() {
        Long id = 1L;

        doNothing().when(favoriteRepository).deleteById(id);

        favoriteService.delete(id);

        verify(favoriteRepository, times(1)).deleteById(id);
    }
}

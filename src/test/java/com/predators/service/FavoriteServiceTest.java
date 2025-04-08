package com.predators.service;

import com.predators.entity.Favorite;
import com.predators.exception.FavoriteNotFoundException;
import com.predators.repository.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class  FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;
    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllFavorites() {
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(favorite1, favorite2));

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
    void testGetFavoriteById_WhenIdExists() {
        Favorite favorite = new Favorite();
        when(favoriteRepository.findById(1L)).thenReturn(java.util.Optional.of(favorite));

        Favorite result = favoriteService.getById(1L);

        assertEquals(favorite, result);
        verify(favoriteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFavoriteById_WhenNotExist() {
        when(favoriteRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        FavoriteNotFoundException exception = assertThrows(
                FavoriteNotFoundException.class,
                () -> favoriteService.getById(99L)
        );

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

package com.predators.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predators.entity.Favorite;
import com.predators.service.FavoriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllFavorites() throws Exception {
        when(favoriteService.getAll()).thenReturn(List.of(new Favorite()));

        mockMvc.perform(get("/api/favorites"))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).getAll();
    }

    @Test
    void testCreateFavorite() throws Exception {
        Favorite favorite = new Favorite();
        when(favoriteService.create(any(Favorite.class))).thenReturn(favorite);

        mockMvc.perform(post("/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favorite)))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).create(any(Favorite.class));
    }

    @Test
    void testGetFavoriteById() throws Exception {
        Favorite favorite = new Favorite();
        when(favoriteService.getById(1L)).thenReturn(favorite);

        mockMvc.perform(get("/api/favorites/1"))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).getById(1L);
    }

    @Test
    void testDeleteFavorite() throws Exception {
        doNothing().when(favoriteService).delete(1L);

        mockMvc.perform(delete("/api/favorites/1"))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).delete(1L);
    }
}

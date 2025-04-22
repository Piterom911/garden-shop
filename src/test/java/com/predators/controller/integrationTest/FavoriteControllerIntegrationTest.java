package com.predators.controller.integrationTest;

import com.predators.entity.Favorite;
import com.predators.entity.Product;
import com.predators.entity.ShopUser;
import com.predators.entity.enums.Role;
import com.predators.repository.FavoriteRepository;
import com.predators.repository.ProductJpaRepository;
import com.predators.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
public class FavoriteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private UserJpaRepository shopUserRepository;

    private ShopUser user;
    private Product product;

    @BeforeEach
    void setup() {
        user = ShopUser.builder()
                .email("user")
                .role(Role.CLIENT)
                .favorites(new ArrayList<>())
                .build();
        shopUserRepository.save(user);

        product = Product.builder()
                .name("Test Product")
                .price(BigDecimal.valueOf(10.0))
                .build();
        productRepository.save(product);
    }

    @Test
    void testCreateFavorite() throws Exception {
        mockMvc.perform(post("/v1/favorites/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(product.getId()));
    }

    @Test
    void testGetAllFavorites() throws Exception {
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        favoriteRepository.save(favorite);

        mockMvc.perform(get("/v1/favorites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(product.getId()));
    }

    @Test
    void testGetFavoriteById() throws Exception {
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        favoriteRepository.save(favorite);

        mockMvc.perform(get("/v1/favorites/{id}", favorite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(product.getId()));
    }

    @Test
    void testDeleteFavorite() throws Exception {
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        favoriteRepository.save(favorite);

        mockMvc.perform(delete("/v1/favorites/{id}", favorite.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
package com.predators.controller;

import com.predators.dto.converter.FavoriteConverter;
import com.predators.dto.favorite.FavoriteResponseDto;
import com.predators.entity.Favorite;
import com.predators.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    private final FavoriteConverter favoriteConverter;

    @GetMapping
    public ResponseEntity<List<FavoriteResponseDto>> getAll() {
        return new ResponseEntity<>(favoriteService.getAll().stream()
                .map(favoriteConverter::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<FavoriteResponseDto> create(@PathVariable(name = "id") Long productId) {
        Favorite favorite = favoriteService.create(productId);
        return new ResponseEntity<>(favoriteConverter.toDto(favorite), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(favoriteConverter.toDto(favoriteService.getById(id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        favoriteService.delete(id);
    }
}

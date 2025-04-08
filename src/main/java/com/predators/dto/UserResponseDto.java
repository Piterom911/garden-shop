package com.predators.dto;

import com.predators.entity.Favorite;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponseDto(Long id,
                              String name,
                              String email,
                              String phoneNumber,
                              List<Favorite> favorites) {
}
package com.predators.dto;

import lombok.Builder;

@Builder
public record UserRequestDto(String name,
                             String email,
                             String phone,
                             String password) {
}

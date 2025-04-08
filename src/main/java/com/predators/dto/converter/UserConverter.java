package com.predators.dto.converter;

import com.predators.dto.UserRequestDto;
import com.predators.dto.UserResponseDto;
import com.predators.entity.User;
import com.predators.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private final UserService userService;

    public UserConverter(UserService userService) {
        this.userService = userService;
    }


    public User toEntity(UserRequestDto userDto) {

        return User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .phoneNumber(userDto.phone())
                .passwordHash(userDto.password())
                .build();
    }

    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
//                .favorites(user.getFavorite())
                .build();
    }
}

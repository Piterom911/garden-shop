package com.predators.dto.converter;

import com.predators.dto.user.UserRequestDto;
import com.predators.dto.user.UserResponseDto;
import com.predators.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserRequestDto, UserResponseDto, User> {

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
                .build();
    }
}

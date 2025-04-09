package com.predators.controller;

import com.predators.dto.user.UserRequestDto;
import com.predators.dto.user.UserResponseDto;
import com.predators.dto.converter.UserConverter;
import com.predators.entity.User;
import com.predators.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        List<UserResponseDto> usersDto = users.stream().map(userConverter::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userDto) {
        User user = userConverter.toEntity(userDto);
        UserResponseDto dto = userConverter.toDto(userService.create(user));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);
        UserResponseDto userDto = userConverter.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

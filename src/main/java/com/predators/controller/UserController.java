package com.predators.controller;

import com.predators.dto.UserRequestDto;
import com.predators.dto.UserResponseDto;
import com.predators.dto.converter.UserConverter;
import com.predators.entity.User;
import com.predators.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    private final UserConverter userConverter;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        List<UserResponseDto> usersDto = users.stream().map(userConverter::toDto).toList();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userDto) {
        User user = userConverter.toEntity(userDto);
        UserResponseDto dto = userConverter.toDto(userService.create(user));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable(name = "id") Long id) {
        UserResponseDto userDto = userConverter.toDto(userService.create(userService.getById(id)));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

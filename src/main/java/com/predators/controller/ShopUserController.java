package com.predators.controller;

import com.predators.dto.converter.ShopUserConverter;
import com.predators.dto.user.UserRequestDto;
import com.predators.dto.user.UserResponseDto;
import com.predators.entity.ShopUser;
import com.predators.security.AuthenticationService;
import com.predators.security.model.SignInRequest;
import com.predators.security.model.SignInResponse;
import com.predators.service.ShopUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class ShopUserController {

    private final ShopUserService shopUserService;

    private final ShopUserConverter shopUserConverter;

    private final AuthenticationService authenticationService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<ShopUser> users = shopUserService.getAll();
        List<UserResponseDto> usersDto = users.stream().map(shopUserConverter::toDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userDto) {
        ShopUser user = shopUserConverter.toEntity(userDto);
        UserResponseDto dto = shopUserConverter.toDto(shopUserService.create(user));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public SignInResponse login(@RequestBody SignInRequest request) {
        return authenticationService.authenticate(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDto> getById(@PathVariable(name = "id") Long id) {
        ShopUser user = shopUserService.getById(id);
        UserResponseDto userDto = shopUserConverter.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        shopUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto userDto) {
        ShopUser update = shopUserService.update(userDto);
        return new ResponseEntity<>(shopUserConverter.toDto(update), HttpStatus.ACCEPTED);
    }
}

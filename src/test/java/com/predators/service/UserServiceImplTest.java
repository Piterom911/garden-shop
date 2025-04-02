package com.predators.service;

import com.predators.entity.User;
import com.predators.exception.UserNotFoundException;
import com.predators.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetAll() {
        userService.getAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        userService.create(new User());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetById() {
        Long id = 1L;
        User expectedUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getById(id);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userService.getById(2L));
    }

    @Test
    void delete() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
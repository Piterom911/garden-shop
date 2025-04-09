package com.predators.service;

import com.predators.entity.User;
import com.predators.entity.enums.Role;
import com.predators.exception.UserNotFoundException;
import com.predators.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userRepository;

    public UserServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.CLIENT);
        }
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Cannot delete user: no user found with id " + id);
        }
        userRepository.deleteById(id);
    }
}

package com.predators.service;

import com.predators.entity.ShopUser;
import com.predators.exception.UserAlreadyExistsException;
import com.predators.exception.UserNotFoundException;
import com.predators.repository.UserJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopUserServiceImpl implements ShopUserService {

    private final UserJpaRepository userRepository;

    public ShopUserServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<ShopUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ShopUser create(ShopUser shopUser) {
        Optional<ShopUser> existingUser = userRepository.findByEmail(shopUser.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with such email exists");
        }

        return userRepository.save(shopUser);
    }

    @Override
    public ShopUser getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        Optional<ShopUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Cannot delete user: no user found with id " + id);
        }
    }

    @Override
    public ShopUser getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public ShopUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();
        return getByEmail(email);
    }

}

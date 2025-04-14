package com.predators.service;

import com.predators.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User create(User user);

    User getById(Long id);

    void delete(Long id);

    User getByEmail(String email);
}

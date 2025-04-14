package com.predators.service;

import com.predators.entity.ShopUser;

import java.util.List;

public interface ShopUserService {

    List<ShopUser> getAll();

    ShopUser create(ShopUser user);

    ShopUser getById(Long id);

    void delete(Long id);

    ShopUser getByEmail(String email);
}

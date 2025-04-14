package com.predators.security;

import com.predators.entity.User;

public interface JwtService  {

    String generateToken(User user);

}

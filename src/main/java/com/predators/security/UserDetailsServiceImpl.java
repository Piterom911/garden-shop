package com.predators.security;

import com.predators.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.predators.entity.User byEmail = userService.getByEmail(email);
        User user = new User(byEmail.getEmail(), byEmail.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(byEmail.getRole().name())));
        return user;
    }
}


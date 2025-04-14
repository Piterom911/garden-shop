package com.predators.security;

import com.predators.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${jwttoken.sign.secret.key}") String jjwtSecretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jjwtSecretKey));
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("login", user.getEmail());
        claims.put("role", user.getRole());
        return generateToken(claims, user);
    }

    private String generateToken(Map<String, Object> claims, User shopUser) {
        return Jwts.builder()
                .claims()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (3600 * 1000)))
                .subject(shopUser.getEmail())
                .add(claims)
                .and()
                .signWith(secretKey)
                .compact();
    }
}




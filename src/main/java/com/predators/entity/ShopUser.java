package com.predators.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.predators.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class ShopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CLIENT;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonBackReference
    private Set<Favorite> favorites = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonBackReference
    private Cart cart;
}

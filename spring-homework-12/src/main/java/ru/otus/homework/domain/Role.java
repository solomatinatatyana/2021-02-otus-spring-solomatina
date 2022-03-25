package ru.otus.homework.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN, USER;

    /*public Set<SimpleGrantedAuthority> getAuthorities() {
        return new HashSet<>().stream()
                .map(permission -> new SimpleGrantedAuthority(ADMIN.name()))
                .collect(Collectors.toSet());
    }*/
}

package com.alejandro.microservices.api_wallet.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Por simplicidad, creamos usuarios hardcodeados
        // En una aplicación real, esto vendría de una base de datos
        if ("admin".equals(username)) {
            // Usuario admin con rol ADMIN y USER
            List<SimpleGrantedAuthority> adminAuthorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
            );
            return new User("admin", "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG", // password: password
                    adminAuthorities);
        } else if ("user".equals(username)) {
            // Usuario normal con rol USER
            List<SimpleGrantedAuthority> userAuthorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
            );
            return new User("user", "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG", // password: password
                    userAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}

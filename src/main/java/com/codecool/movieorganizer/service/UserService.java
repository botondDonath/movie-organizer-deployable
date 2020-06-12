package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.MovieAppUser;
import com.codecool.movieorganizer.model.UserCredentials;
import com.codecool.movieorganizer.repository.db.MovieAppUserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    private final MovieAppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(MovieAppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public MovieAppUser register(UserCredentials userCredentials) {
        if (users.existsByUsername(userCredentials.getUsername())) return null;
        MovieAppUser user = MovieAppUser.builder()
            .username(userCredentials.getUsername())
            .password(passwordEncoder.encode(userCredentials.getPassword()))
            .roles(Arrays.asList("USER"))
            .build();
        return users.save(user);
    }

    public HttpHeaders constructTokenCookieHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        // Once I included the Path directive set to "/", it worked
        headers.add("Set-Cookie", String.format("JWT=%s; httpOnly; Path=/", token));
        return headers;
    }
}

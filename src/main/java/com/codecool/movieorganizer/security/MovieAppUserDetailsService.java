package com.codecool.movieorganizer.security;

import com.codecool.movieorganizer.model.MovieAppUser;
import com.codecool.movieorganizer.repository.db.MovieAppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieAppUserDetailsService implements UserDetailsService {

    private final MovieAppUserRepository repository;

    public MovieAppUserDetailsService(MovieAppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MovieAppUser user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username doesn't exist"));
        return new User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
    }
}

package com.codecool.movieorganizer.repository.db;

import com.codecool.movieorganizer.model.MovieAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieAppUserRepository extends JpaRepository<MovieAppUser, Long> {
    boolean existsByUsername(String username);
    Optional<MovieAppUser> findByUsername(String username);
}

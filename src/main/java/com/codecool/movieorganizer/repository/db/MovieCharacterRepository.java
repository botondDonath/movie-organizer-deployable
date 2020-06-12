package com.codecool.movieorganizer.repository.db;

import com.codecool.movieorganizer.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    Set<MovieCharacter> findAllByNameIn(Set<String> names);
}

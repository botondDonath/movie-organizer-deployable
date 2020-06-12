package com.codecool.movieorganizer.repository.db;

import com.codecool.movieorganizer.model.CharacterActorMovieMap;
import com.codecool.movieorganizer.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CharacterActorMovieMapRepository extends JpaRepository<CharacterActorMovieMap, Long> {
    Set<CharacterActorMovieMap> findAllByMovie(Movie movie);
}

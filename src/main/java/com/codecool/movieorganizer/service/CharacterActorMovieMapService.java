package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.Movie;
import com.codecool.movieorganizer.model.MovieCharacter;
import com.codecool.movieorganizer.repository.db.CharacterActorMovieMapRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CharacterActorMovieMapService {

    private final CharacterActorMovieMapRepository mapRepository;
    private final MovieCharacterService movieCharacterService;

    public CharacterActorMovieMapService(CharacterActorMovieMapRepository mapRepository, MovieCharacterService movieCharacterService) {
        this.mapRepository = mapRepository;
        this.movieCharacterService = movieCharacterService;
    }

    public void synchronizeAdded(Movie movie) {
        if (movie.getCharacterActorMovieMaps() == null) {
            movie.setCharacterActorMovieMaps(new HashSet<>());
            return;
        }
        Set<MovieCharacter> existingCharacters = movieCharacterService.findCharactersIn(movie.getCharacterActorMovieMaps());
        movie.getCharacterActorMovieMaps().forEach(map -> {
            if (existingCharacters.contains(map.getMovieCharacter())) {
                map.setMovieCharacter(existingCharacters.stream().filter(map.getMovieCharacter()::equals).findFirst().get());
            }
            map.setMovie(movie);
        });
    }
}

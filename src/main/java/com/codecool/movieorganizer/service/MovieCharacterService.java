package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.CharacterActorMovieMap;
import com.codecool.movieorganizer.model.MovieCharacter;
import com.codecool.movieorganizer.repository.db.MovieCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieCharacterService {

    private MovieCharacterRepository characterRepository;

    public MovieCharacterService(MovieCharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Set<MovieCharacter> findCharactersIn(Set<CharacterActorMovieMap> characterActorMovieMaps) {
        Set<String> names = characterActorMovieMaps
                .stream()
                .map(map -> map.getMovieCharacter().getName())
                .collect(Collectors.toSet());
        return characterRepository.findAllByNameIn(names);
    }
}

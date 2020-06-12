package com.codecool.movieorganizer.config;

import com.codecool.movieorganizer.model.*;
import com.codecool.movieorganizer.repository.db.MovieAppUserRepository;
import com.codecool.movieorganizer.service.dao.MovieDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final MovieAppUserRepository users;
    private final MovieDao movieDao;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(MovieAppUserRepository users, MovieDao movieDao, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.movieDao = movieDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Set<String> categories = new HashSet<>(Arrays.asList("dark", "sci-fi"));
        MovieCharacter ironMan = MovieCharacter.builder().name("Iron Man").build();
        Artist robertDowney = Artist.builder().name("Robert Downey Jr.").build();
        Movie avengers = Movie.builder()
            .title("The Avengers")
            .addedTime(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .categories(categories)
            .build();

        CharacterActorMovieMap characterActorMovieMap = new CharacterActorMovieMap(avengers, robertDowney, ironMan);
        Set<CharacterActorMovieMap> maps = new HashSet<>();
        maps.add(characterActorMovieMap);
        avengers.setCharacterActorMovieMaps(maps);
        characterActorMovieMap.setMovie(avengers);
        movieDao.add(avengers);

        MovieAppUser user = MovieAppUser.builder()
            .username("user").password(passwordEncoder.encode("password"))
            .roles(Arrays.asList("USER"))
            .build();
        MovieAppUser admin = MovieAppUser.builder()
            .username("admin").password(passwordEncoder.encode("password"))
            .roles(Arrays.asList("USER", "ADMIN"))
            .build();

        users.saveAll(Arrays.asList(user, admin));
    }
}

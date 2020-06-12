package com.codecool.movieorganizer.service.dao.jpa;

import com.codecool.movieorganizer.config.StorageProperties;
import com.codecool.movieorganizer.model.Movie;
import com.codecool.movieorganizer.service.CharacterActorMovieMapService;
import com.codecool.movieorganizer.service.MovieCharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "storage.type=db")
@Import({MovieDaoDb.class, StorageProperties.class, CharacterActorMovieMapService.class, MovieCharacterService.class})
@ActiveProfiles("test")
class MovieDaoDbTest {

    @Autowired
    private MovieDaoDb movieDao;

    @BeforeEach
    void setUp() {
        Set<String> categories = new HashSet<>(Arrays.asList("dark", "sci-fi"));

        Movie alien = Movie.builder()
            .title("alien")
            .addedTime(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .categories(categories)
            .build();
        movieDao.add(alien);
    }

    @Test
    void categoriesArePersistedWithMoviesCorrectly() {
        Movie joker = Movie.builder()
            .title("joker")
            .addedTime(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .categories(new HashSet<>(Arrays.asList("Drama", "Crime")))
            .build();
        Movie jokerFromDb = movieDao.add(joker);
        assertSame(joker, jokerFromDb);
        assertThat(movieDao.find(joker.getId()).getCategories()).hasSize(2);
    }

    @Test
    void whenMoviesAreUpdated_CategoriesShouldBeMappedToThem() {
        Movie alien = movieDao.findByTitle("alien");
        Movie newAlien = Movie.builder()
            .title("alien")
            .categories(new HashSet<>(Arrays.asList("extra", "dark", "sci-fi")))
            .build();
        movieDao.update(alien.getId(), newAlien);

        assertThat(alien.getCategories()).hasSize(3);
    }

    @Test
    void addNewMovieThanDeleteItShouldNotBeIn() {

        Movie joker = Movie.builder()
            .title("joker")
            .addedTime(LocalDateTime.now())
            .lastModified(LocalDateTime.now())
            .categories(new HashSet<>(Arrays.asList("drama", "crime")))
            .build();
        movieDao.add(joker);
        movieDao.remove(movieDao.findByTitle("alien").getId());
        assertThat(movieDao.findByTitle("alien")).isEqualTo(null);
    }

    @Test
    void whenMoviesAreUpdated_AndAnExistingCategoryIsAttachedToThem_ItShouldGoDownTheHappyPath() {
        Movie alien = movieDao.findByTitle("alien");
        Movie newAlien = Movie.builder()
            .title("alien")
            .categories(new HashSet<>(Arrays.asList("dark", "sci-fi", "horror")))
            .build();

        assertDoesNotThrow(() -> movieDao.update(alien.getId(), newAlien));
        assertThat(alien.getCategories()).hasSize(3);
    }
}
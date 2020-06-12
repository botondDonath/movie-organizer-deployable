package com.codecool.movieorganizer.service.dao.jpa;

import com.codecool.movieorganizer.model.Movie;
import com.codecool.movieorganizer.repository.db.MovieRepository;
import com.codecool.movieorganizer.service.CharacterActorMovieMapService;
import com.codecool.movieorganizer.service.dao.MovieDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Qualifier("movieDao")
@ConditionalOnProperty(prefix = "storage", name = "type", havingValue = "db")
public class MovieDaoDb implements MovieDao {

    private final MovieRepository movieRepository;
    private final CharacterActorMovieMapService characterActorMovieMapService;
    private final int MOVIES_PER_PAGE = 5;

    public MovieDaoDb(MovieRepository movieRepository,
                      CharacterActorMovieMapService characterActorMovieMapService) {
        this.movieRepository = movieRepository;
        this.characterActorMovieMapService = characterActorMovieMapService;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie find(long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public Page<Movie> findByPage(int page) {
        Sort sort = Sort.by("lastModified").descending();
        Pageable pageable = PageRequest.of(page, MOVIES_PER_PAGE, sort);
        return movieRepository.findBy(pageable);
    }

    @Override
    public Movie add(Movie movie) {
        LocalDateTime createdAt = LocalDateTime.now();
        movie.setAddedTime(createdAt);
        movie.setLastModified(createdAt);
        characterActorMovieMapService.synchronizeAdded(movie);
        return movieRepository.saveAndFlush(movie);
    }

    @Override
    @Transactional
    public Movie update(long id, Movie movie) {
        Movie toUpdate = movieRepository.findById(id).orElse(null);
        if (toUpdate == null) return null;

        toUpdate.setTitle(movie.getTitle());
        toUpdate.setDirector(movie.getDirector());
        toUpdate.setReleaseDate(movie.getReleaseDate());
        toUpdate.setImageURL(movie.getImageURL());
        toUpdate.setCategories(movie.getCategories());
        toUpdate.setPlot(movie.getPlot());
        toUpdate.setLastModified(LocalDateTime.now());
        return toUpdate;
    }

    @Override
    public String attachCategory(long movieId, String category) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        movie.getCategories().add(category);
        movie.setLastModified(LocalDateTime.now());
        movieRepository.save(movie);
        return movie.getCategories()
                    .stream()
                    .filter(saved -> saved.equals(category))
                    .findFirst().orElseThrow();
    }

    @Override
    @Transactional
    public void detachCategory(long movieId, String category) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        movie.getCategories().remove(category);
        movie.setLastModified(LocalDateTime.now());
    }

    @Override
    public boolean remove(long id) {
        Movie movie = find(id);
        if(movie != null) {
            movieRepository.delete(movie);
            return true;
        }
        return false;
    }

}

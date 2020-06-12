package com.codecool.movieorganizer.controller;

import com.codecool.movieorganizer.model.Movie;
import com.codecool.movieorganizer.service.MovieDTO;
import com.codecool.movieorganizer.service.dao.MovieDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieDao movieDao;
    private final Logger logger;

    public MovieController(@Qualifier("movieDao") MovieDao movieDao,
                           @Qualifier("movieControllerLogger") Logger logger)
    {
        this.movieDao = movieDao;
        this.logger = logger;
    }

    @PostMapping
    public Movie add(@RequestBody Movie movie) {
        logger.info("POST request arrived to endpoint /movies movie data: " + movie.toString());
        Movie addedMovie = movieDao.add(movie);
        logger.info("POST request processed on endpoint /movies movie added with following data:" + addedMovie.toString());
        return addedMovie;
    }

    @GetMapping
    public MovieDTO listBy(@RequestParam Integer page) {
        logger.info("GET request arrived to endpoint /movies?page=" + page);
        Page<Movie> movies = movieDao.findByPage(page - 1);
        logger.info("GET request processed on endpoint /movies?page=" + page);
        return new MovieDTO((int) movies.getTotalElements(), movies.getContent());
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable("id") String id, @RequestBody Movie movie) throws Exception {
        return movieDao.update(Long.parseLong(id), movie);
    }

    @PostMapping("/{id}/categories")
    public String attachCategory(@PathVariable("id") String id, @RequestBody String categoryName) {
        return movieDao.attachCategory(Long.parseLong(id), categoryName);
    }

    @DeleteMapping("/{movieId}/categories/{category}")
    public void detachCategory(@PathVariable("movieId") String movieId, @PathVariable("category") String category) {
        movieDao.detachCategory(Long.parseLong(movieId), category);
    }

    @DeleteMapping("/{id}")
    public boolean deleteMovie(@PathVariable("id") String id) {
        logger.info("DELETE request arrived to endpoint /movies/" + id);
        return movieDao.remove(Long.parseLong(id));
    }
}

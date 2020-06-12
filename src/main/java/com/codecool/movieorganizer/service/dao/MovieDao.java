package com.codecool.movieorganizer.service.dao;

import com.codecool.movieorganizer.model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll();
    Movie find(long id);
    Page<Movie> findByPage(int page);
    Movie add(Movie movie);
    Movie update(long id, Movie movie);
    String attachCategory(long movieId, String category);
    void detachCategory(long movieId, String category);
    boolean remove(long id);
}

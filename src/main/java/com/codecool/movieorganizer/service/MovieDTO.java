package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.Movie;

import java.util.List;

public class MovieDTO {

    private int totalMovieCount;
    private List<Movie> movies;

    public MovieDTO(int totalMovieCount, List<Movie> movies) {
        this.totalMovieCount = totalMovieCount;
        this.movies = movies;
    }

    public int getTotalMovieCount() {
        return totalMovieCount;
    }

    public void setTotalMovieCount(int totalMovieCount) {
        this.totalMovieCount = totalMovieCount;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

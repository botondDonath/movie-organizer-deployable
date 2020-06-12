package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoviePaginator {

    private final int NUMBER_OF_MOVIES_IN_PAGE = 5;

    public List<Movie> paginate(List<Movie> allMovies, int page) {
        List<Movie> resultArray = new ArrayList<>();
        for (int index = firstIndexInPage(page); isAtPageLastMovieIndex(index, page, allMovies); index++) {
            resultArray.add(allMovies.get(index));
        }
        return resultArray;
    }

    private boolean isAtPageLastMovieIndex(int index, int page, List<Movie> movieList) {
        return index < movieList.size() && index < (page - 1) * NUMBER_OF_MOVIES_IN_PAGE + NUMBER_OF_MOVIES_IN_PAGE;
    }

    private int firstIndexInPage(int page) {
        return (page - 1) * NUMBER_OF_MOVIES_IN_PAGE;
    }
}

package com.codecool.movieorganizer.service;

import com.codecool.movieorganizer.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviePaginatorTest {
    private List<Movie> testMovieList;
    private MoviePaginator paginator;

    @Test
    void test_PaginateWithPageIndexInRange() {
        List<Movie> pageInRange = paginator.paginate(testMovieList,1);
        assertEquals(5, pageInRange.size());
        assertEquals(0, pageInRange.get(0).getId());
        assertEquals(4, pageInRange.get(4).getId());
    }

    @Test
    void test_PaginateLastPage() {
        List<Movie> lastPage = paginator.paginate(testMovieList,2);
        assertEquals(2, lastPage.size());
        assertEquals(5, lastPage.get(0).getId());
        assertEquals(6, lastPage.get(1).getId());
    }

    @Test
    void test_PaginateOutOfRangePage(){
        List<Movie> outOfRangePage = paginator.paginate(testMovieList,3);
        assertEquals(0, outOfRangePage.size());
    }

    @BeforeEach
    void setUp(){
        paginator = new MoviePaginator();
        testMovieList = new ArrayList<>();
        for(int index=0;index<7;index++){
            testMovieList.add(new Movie(index, "test", new HashSet<>() , "testDirector", 5, "test", "test"));
        }
    }
}
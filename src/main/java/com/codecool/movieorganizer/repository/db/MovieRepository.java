package com.codecool.movieorganizer.repository.db;

import com.codecool.movieorganizer.model.Movie;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@ConditionalOnProperty(prefix = "storage", name = "type", havingValue = "db")
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    Page<Movie> findBy(Pageable pageAble);
}

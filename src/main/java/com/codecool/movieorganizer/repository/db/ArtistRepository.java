package com.codecool.movieorganizer.repository.db;

import com.codecool.movieorganizer.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}

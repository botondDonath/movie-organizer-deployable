package com.codecool.movieorganizer.model;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@DynamicUpdate
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> categories;
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<CharacterActorMovieMap> characterActorMovieMaps;
    private String director;
    private Integer releaseDate;
    private String plot;
    private String imageURL;
    private LocalDateTime addedTime;
    private LocalDateTime lastModified;

    public Movie(long id, String title, Set<String> categories, String director, Integer releaseDate, String plot, String imageURL) {
        this.id = id;
        this.title = title;
        this.categories = categories;
        this.director = director;
        this.releaseDate = releaseDate;
        this.plot = plot;
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(addedTime, movie.addedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash("movie", addedTime);
    }
}

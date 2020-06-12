package com.codecool.movieorganizer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
public class CharacterActorMovieMap {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Movie movie;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Artist actor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private MovieCharacter movieCharacter;

    public CharacterActorMovieMap() {
    }

    public CharacterActorMovieMap(Movie movie, Artist actor, MovieCharacter movieCharacter) {
        this.movie = movie;
        this.actor = actor;
        this.movieCharacter = movieCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterActorMovieMap that = (CharacterActorMovieMap) o;
        return Objects.equals(movie, that.movie) &&
               Objects.equals(actor, that.actor) &&
               Objects.equals(movieCharacter, that.movieCharacter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, actor, movieCharacter);
    }
}

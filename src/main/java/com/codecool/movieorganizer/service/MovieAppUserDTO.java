package com.codecool.movieorganizer.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieAppUserDTO {

    private String username;

    public MovieAppUserDTO(String username) {
        this.username = username;
    }
}

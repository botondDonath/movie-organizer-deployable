package com.codecool.movieorganizer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDto {

    private String username;
    private String err;

    public LoginResponseDto(String username) {
        this.username = username;
    }

    public LoginResponseDto(String username, String err) {
        this.username = username;
        this.err = err;
    }
}

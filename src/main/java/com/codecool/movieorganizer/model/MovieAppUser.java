package com.codecool.movieorganizer.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}

package com.codecool.movieorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MovieOrganizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieOrganizerApplication.class, args);
    }
}
